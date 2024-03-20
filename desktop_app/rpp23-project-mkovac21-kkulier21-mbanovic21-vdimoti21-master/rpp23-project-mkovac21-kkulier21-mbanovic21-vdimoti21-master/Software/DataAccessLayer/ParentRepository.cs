using EntityLayer;
using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class ParentRepository : IDisposable
    {
        public PMSModel Context { get; set; }
        public DbSet<Parent> Parents  { get; set; }

        public ParentRepository(PMSModel context)
        {
            Context = context;
            Parents = Context.Set<Parent>();
        }

        public IQueryable<Parent> GetAllParents()
        {
            var query = from p in Parents
                        select p;

            return query;
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}
