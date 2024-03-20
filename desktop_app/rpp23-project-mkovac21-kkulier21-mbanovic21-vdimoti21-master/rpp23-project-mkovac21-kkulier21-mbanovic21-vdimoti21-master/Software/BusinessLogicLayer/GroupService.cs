using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class GroupService
    {
        public List<Group> GetAllGroups()
        {
            using (var repo = new GroupRepository(new PMSModel()))
            {
                return repo.GetAllGroups().ToList();
            }

        }

        public List<Group> GetGroupByName(string name)
        {
            using(var repo = new GroupRepository(new PMSModel()))
            {
                return repo.GetGroupByName(name).ToList();
            }
           
        }

        public void AddGroup(Group group)
        {
            using (var repo = new GroupRepository(new PMSModel()))
            {
                repo.AddGroup(group);
            }
        }
        public int? GetGroupIdByName(string name)
        {
            using (var repo = new GroupRepository(new PMSModel()))
            {
                return repo.GetGroupIdByName(name);
            }
        }

    }
}
