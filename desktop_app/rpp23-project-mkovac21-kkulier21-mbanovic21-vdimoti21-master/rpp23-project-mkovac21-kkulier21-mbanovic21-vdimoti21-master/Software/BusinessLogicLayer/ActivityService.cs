using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class ActivityService
    {
        public List<DailyActivity> GetAllActivitiesByDay(Day day)
        {
            using(var service =  new ActivityRepository(new PMSModel()))
            {
                return service.GetAllActivitiesByDay(day).ToList();
            }
            
        }
        public void AddActivity(DailyActivity activity,Day day)
        {
            using (var service = new ActivityRepository(new PMSModel()))
            {
                service.AddActivity(activity,day);
            }

        }
    }
}
