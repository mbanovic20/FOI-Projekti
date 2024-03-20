using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer {
    public class AttendanceService {

        private AttendanceRepository attendanceRepository;

        public AttendanceService()
        {
            attendanceRepository= new AttendanceRepository(new PMSModel());
        }

        public void AddAttendance(Attendance attendance) {
            if (attendance == null) throw new ArgumentNullException("attendance");
            if (!attendance.Date.HasValue) {
                attendance.Date = DateTime.Now;
            }
            if (!attendance.isPresent.HasValue) {
                attendance.isPresent = false; 
            }

            attendanceRepository.AddAttendance(attendance);
        
        }
        public List<Attendance> GetPastAttendanceForChild(int childId) {
            return attendanceRepository.GetPastAttendanceForChild(childId);
        }
        public List<Attendance> GetAttendanceByDate(DateTime date) {
            return attendanceRepository.GetAttendanceByDate(date);
        }

        public bool RemoveAttendance(int attendanceId) {
          
            return attendanceRepository.RemoveAttendance(attendanceId);
        }
        public void UpdateAttendance(Attendance attendance) {
            if (attendance == null) {
                throw new ArgumentNullException(nameof(attendance), "Provided attendance is null.");
            }
            attendanceRepository.UpdateAttendance(attendance);
        }
    }
}
