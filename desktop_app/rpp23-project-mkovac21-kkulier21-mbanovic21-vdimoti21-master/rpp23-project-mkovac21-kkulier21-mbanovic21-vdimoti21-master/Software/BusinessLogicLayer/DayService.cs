using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class DayService
    {
        public void addNewDay(Day day)
        {
            using(var repo = new DayRepository(new PMSModel()))
            {
                repo.AddNewDay(day);
            }
        }

        public List<Day> getDaysByWeeklySchdule(int week)
        {
            using (var repo = new DayRepository(new PMSModel()))
            {
                return repo.getDaysByWeeklySchdule(week).ToList();
            }
        }

        public List<User> getUsersByDayId(int id)
        {
            using (var repo = new DayRepository(new PMSModel()))
            {
                return repo.getUsersByDayId(id).ToList();
            }
        }

        public List<Day> getDaysByWeeklySchduleAndUsername(int week, string username)
        {
            using (var repo = new DayRepository(new PMSModel()))
            {
                return repo.getDaysByWeeklySchduleAndUsername(week, username.ToString()).ToList();
            }
        }
    }
}
