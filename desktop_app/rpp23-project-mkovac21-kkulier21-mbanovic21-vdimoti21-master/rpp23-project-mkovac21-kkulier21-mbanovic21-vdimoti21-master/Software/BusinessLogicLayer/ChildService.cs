using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Remoting.Contexts;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class ChildService
    {

        private GroupRepository groupRepository;
        private ChildRepository childRepository;


        public ChildService()
        {
            groupRepository = new GroupRepository(new PMSModel());
            childRepository = new ChildRepository(new PMSModel());
        }

        //Vdran Đimoti
        public List<Child> getChildremFromGroup(Group group)
        {
            using (var repo = new ChildRepository(new PMSModel()))
            {
                return repo.GetChildremFromGroup(group).ToList();
            }
        }
        //Vdran Đimoti
        public List<Child> GetChildByLastName (string lastName)
        {
            using (var repo = new ChildRepository(new PMSModel()))
            {
                return repo.GetChildByLastName(lastName).ToList();
            }
        }

        public List<Child> GetAllChildren() {
        using (var repo = new ChildRepository(new PMSModel())) {
            var child = repo.GetAllChild().ToList();
            return child;
            }
        }

        public void AddChild(Child child) {
            using (var repo = new ChildRepository(new PMSModel())) {
                repo.AddnewChild(child);
            }
        }

        //ZASTO JE OVO TU A NE GROUPSERICE
        public List<string> GetGroupNames() {
            using (var repo = new ChildRepository(new PMSModel())) {
                return repo.GetGroupNames();
            }
        }
        public bool DeleteChild(Child child) {
            using (var repo = new ChildRepository(new PMSModel())) {
                return repo.DeleteChild(child);
            }
        }
        public void UpdateChild(Child child, string groupName) {
            int? groupId = groupRepository.GetGroupIdByName(groupName);
            if (groupId.HasValue) {
                child.Id_Group = groupId.Value;
                childRepository.UpdateChild(child);
            } else {
                throw new Exception("Group not found.");
            }
        }

        public List<Child> getChildrenBySearch(string pattern)
        {
            using (var repo = new ChildRepository(new PMSModel()))
            {
                return repo.getChildBySearch(pattern).ToList();
            }
        }
    }
}
