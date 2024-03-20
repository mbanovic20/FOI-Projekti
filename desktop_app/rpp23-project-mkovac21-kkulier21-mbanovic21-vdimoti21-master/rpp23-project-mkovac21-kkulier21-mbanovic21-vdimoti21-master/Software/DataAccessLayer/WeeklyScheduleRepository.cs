using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class WeeklyScheduleRepository : IDisposable
    {
        public PMSModel Context { get; set; }

        public DbSet<WeeklySchedule> Weeks { get; set; }

        public WeeklyScheduleRepository(PMSModel context)
        {
            Context = context;
            Weeks = Context.Set<WeeklySchedule>();
        }

        public IQueryable<WeeklySchedule> getAllUsers()
        {
            var query = from w in Weeks
                        select w;

            return query;
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}
