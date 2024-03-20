using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class ActivityRepository : IDisposable
    {
        private PMSModel Context;
        private DbSet<DailyActivity> DailyActivity;

        public ActivityRepository(PMSModel context)
        {
            Context = context;
            DailyActivity = Context.Set<DailyActivity>();
        }

        public IQueryable<DailyActivity> GetAllActivitiesByDay(Day day)
        {
            var queri = from a in DailyActivity
                        where a.Days.Any(d => d.Id == day.Id) select a; 
            return queri;
        }
        public void AddActivity(DailyActivity activity,Day day)
        {
            Context.Days.Attach(day);
            activity.Days.Add(day);
            DailyActivity.Add(activity);
            Context.SaveChanges();
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}
