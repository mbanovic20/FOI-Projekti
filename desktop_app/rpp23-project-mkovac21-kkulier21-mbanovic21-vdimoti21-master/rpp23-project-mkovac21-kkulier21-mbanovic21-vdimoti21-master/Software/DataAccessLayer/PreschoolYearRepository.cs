using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class PreschoolYearRepository : IDisposable
    {
        private PMSModel Context;
        private DbSet<PreeschoolYear> PreschoolYear;

        public PreschoolYearRepository(PMSModel context)
        {
            Context = context;
            PreschoolYear = Context.Set<PreeschoolYear>();
        }

        public void addNewPreschoolYear(PreeschoolYear preeschoolYear, List<Group> groups)
        {   
            foreach(var g in groups)
            {
                Context.Groups.Attach(g);
                preeschoolYear.Groups.Add(g);
            }

            PreschoolYear.Add(preeschoolYear);
            Context.SaveChanges();
        }

        public IQueryable<ICollection<Group>>  GetGroupsForYear(string year)
        {
            var queri = from y in PreschoolYear where y.Year == year select y.Groups;
            return queri.AsQueryable();
        }




        public IQueryable<string> GetAllYearsName()
        {
            var queri = from y in PreschoolYear  select y.Year;
            return queri;
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}
