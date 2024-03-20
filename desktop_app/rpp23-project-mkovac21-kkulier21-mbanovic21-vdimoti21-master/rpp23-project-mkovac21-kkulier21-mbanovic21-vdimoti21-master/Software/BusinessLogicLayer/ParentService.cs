using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class ParentService
    {
        public List<Parent> getAllParents()
        {
            using (var context = new ParentRepository(new PMSModel()))
            {
                return context.GetAllParents().ToList();
            }
        }
    }
}
