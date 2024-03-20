using BusinessLogicLayer;

using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace PresentationLayer {
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    /// 
    ///<remarks> Karla Kulier</remarks>
    public partial class Login : Window {

        public Login() {
            InitializeComponent();
        }
       
       
        ///<remarks> Karla Kulier</remarks>
           private void btnLogin_Click(object sender, RoutedEventArgs e) {
               var username = txtUsername.Text;
               var password = txtPassword.Password;
            ///<remarks> Karla Kulier</remarks>
            UserService userService = new UserService();
               var user = userService.ValidateUser(username, password);

               if (user != null) {
                LoggedInUser.Login(user);
                MessageBox.Show($"Logged in as: {LoggedInUser.User.Username}");

                Window window = new MainWindow();
                    window.Show();
                    //MessageBox.Show("Successful login");
                    this.Close();
               } else {
                   MessageBox.Show("Invalid username or password.");
               }
           }



           ///<remarks> Karla Kulier</remarks>
           private void Hyperlink_Click_2(object sender, RoutedEventArgs e) {
               ForgotPassword forgotPassword = new ForgotPassword();
               forgotPassword.Show();
               this.Close(); ;
           }
       }
    }

