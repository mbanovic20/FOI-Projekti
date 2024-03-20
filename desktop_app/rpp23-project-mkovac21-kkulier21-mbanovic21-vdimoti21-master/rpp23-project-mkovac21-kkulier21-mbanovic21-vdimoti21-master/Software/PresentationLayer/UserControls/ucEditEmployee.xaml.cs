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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace PresentationLayer.UserControls {
    /// <summary>
    /// Interaction logic for ucEditEmployee.xaml
    /// </summary>
    public partial class ucEditEmployee : UserControl {
        private MainWindow MainWindow;
        private User currentuser;
        public ucEditEmployee(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
        }

        public void SetEmployeeData(User user) {
            currentuser = user;  

            txtUsername.Text = user.Username;
            txtFirstName.Text = user.FirstName;
            txtLastName.Text = user.LastName;
            txtEmail.Text = user.Email;
            txtPassword.Text = user.Password; 

            txtUsername.IsEnabled = false;
        }

        private void btnSave_Click_1(object sender, RoutedEventArgs e) {
            currentuser.Username = txtUsername.Text;
            currentuser.FirstName = txtFirstName.Text;
            currentuser.LastName = txtLastName.Text;
            currentuser.Email = txtEmail.Text;
            currentuser.Password = txtPassword.Text; 

            UserService userService = new UserService();
            userService.UpdateUser(currentuser);


            MainWindow.controlPanel.Content= new ucAdministeringEmployees(MainWindow);
        }
        private void btnCancel_Click_1(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content= new ucAdministeringEmployees(MainWindow);
        }
    }
}
