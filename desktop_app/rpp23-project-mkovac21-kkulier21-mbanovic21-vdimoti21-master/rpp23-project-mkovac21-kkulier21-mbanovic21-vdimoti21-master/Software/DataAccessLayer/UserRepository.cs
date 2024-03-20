using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Runtime.InteropServices;
using System.Runtime.Remoting.Contexts;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class UserRepository : IDisposable
    {
        public PMSModel Context { get; set; }

        public DbSet<User> Users { get; set; }

        public UserRepository(PMSModel context)
        {
            Context = context;
            Users = Context.Set<User>();
        }
        ///<remarks> Karla Kulier</remarks>
        public void AddnewUser(User user) {
            Users.Add(user);
            Context.SaveChanges();

        }
        ///<remarks> Karla Kulier</remarks>
        public User GetUserByUsername(string username, string password)
        {
            var user = Users.FirstOrDefault(u => u.Username == username && u.Password == password);
            return user;
        }

        public IQueryable<User> getAllUsers()
        {
            var query = from u in Users
                        select u;

            return query;
        }

        public IQueryable<string> getUserEmails()
        {
            var query = from u in Users
                        select u.Email;

            return query;
        }
        ///<remarks> Karla Kulier</remarks>    
        public bool DeleteUser(User user) {
            try {
                Users.Attach(user);
                Users.Remove(user);
                Context.SaveChanges();
                return true; 
            } catch (Exception ex) {
                Console.WriteLine(ex.Message);
                return false;
            }
        }
        ///<remarks> Karla Kulier</remarks
        public void UpdateUser(User user)
        {
            var existingUser = Context.Users.FirstOrDefault(u => u.Id == user.Id);
            if (existingUser != null)
            {
                existingUser.Username = user.Username;
                existingUser.FirstName = user.FirstName;
                existingUser.LastName = user.LastName;
                existingUser.Email = user.Email;
                existingUser.Password = user.Password;
                Context.SaveChanges();
            }
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}    


