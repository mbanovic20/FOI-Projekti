using EntityLayer;
using DataAccessLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Remoting.Contexts;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class PreschoolyearService
    {
        public void addNewPreschoolYear(PreeschoolYear preeschoolYear, List<Group> group)
        {
            using (var repo = new PreschoolYearRepository(new PMSModel()))
            {
                repo.addNewPreschoolYear(preeschoolYear, group);
            }
        }
        public List<Group> GetGroupsForYear(string year)
        {
            using (var repo = new PreschoolYearRepository(new PMSModel()))
            {
                return repo.GetGroupsForYear(year).SelectMany(x=>x).ToList();
            }
            
        }
        public List<string> GetAllYears()
        {
            using (var repo = new PreschoolYearRepository(new PMSModel()))
            {
                return repo.GetAllYearsName().ToList();
            }

        }
    }
}
