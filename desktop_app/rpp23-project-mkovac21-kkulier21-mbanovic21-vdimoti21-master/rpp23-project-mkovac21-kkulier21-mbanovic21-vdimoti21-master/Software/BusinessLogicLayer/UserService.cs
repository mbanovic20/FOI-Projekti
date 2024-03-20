using DataAccessLayer;
using EntityLayer;
using Microsoft.Win32;
using Microsoft.SqlServer.Server;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class UserService
    {
        public List<User> getUsers()
        {
            using (var repo = new UserRepository(new PMSModel()))
            {
                return repo.getAllUsers().ToList();
            }
        }

        public void AddUser(User user)
        {
            using (var repo = new UserRepository(new PMSModel()))
            {
                repo.AddnewUser(user);
            }
        }

        ///<remarks> Karla Kulier</remarks>
        public User ValidateUser(string username, string password)
        {
            using (var repo = new UserRepository(new PMSModel()))
            {
                var user = repo.GetUserByUsername(username, password);

                return user;
            }
        }
        ///<remarks> Karla Kulier</remarks>
        public List<User> GetUsers()
        {
            using (var repo = new UserRepository(new PMSModel()))
            {

                var users = repo.getAllUsers().ToList();
                return users;
            }
        }
        ///<remarks> Karla Kulier</remarks>

        public bool DeleteUser(User user)
        {
            using (var repo = new UserRepository(new PMSModel()))
            {
                bool isDeleted = repo.DeleteUser(user);
                return isDeleted;
            }
        }
        ///<remarks> Karla Kulier</remarks>
        public void UpdateUser(User user)
        {
            using (var repo = new UserRepository(new PMSModel()))
            {
                repo.UpdateUser(user);
            }
        }
    }
}


