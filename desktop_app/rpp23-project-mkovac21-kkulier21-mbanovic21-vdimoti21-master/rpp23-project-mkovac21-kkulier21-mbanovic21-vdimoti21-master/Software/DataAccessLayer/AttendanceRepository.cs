using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer {
    public class AttendanceRepository {

        private PMSModel Context;
        private DbSet<Attendance> Attendances;

        public AttendanceRepository(PMSModel context)
        {
            Context = context;
            Attendances = Context.Set<Attendance>();
        }

        public void AddAttendance(Attendance attendance) {
            Attendances.Add(attendance);
            Context.SaveChanges();
        }
        public List<Attendance> GetPastAttendanceForChild(int childId) {
            return Attendances.Where(a => a.Id_Child == childId).ToList();
        }
        public List<Attendance> GetAttendanceByDate(DateTime date) {
            return Attendances
                .Where(a => DbFunctions.TruncateTime(a.Date) == date.Date)
                .ToList();
        }
        public bool RemoveAttendance(int attendanceId) {
            try {
                var attendance = Attendances.Find(attendanceId);
                if (attendance != null) {
                    Attendances.Remove(attendance);
                    Context.SaveChanges();
                    return true;
                }
                return false;
            } catch (Exception ex) {
                Console.WriteLine(ex.Message);
                return false;
            }
        }
        public void UpdateAttendance(Attendance updatedAttendance) {
            var existingAttendance = Context.Attendances.FirstOrDefault(a => a.Id == updatedAttendance.Id);
            if (existingAttendance != null) {
                existingAttendance.Date = updatedAttendance.Date;
                existingAttendance.isPresent = updatedAttendance.isPresent;

                Context.SaveChanges();
            } else {
                throw new ArgumentException("Attendance record not found.");
            }
        }
    }
}
