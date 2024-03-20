using BusinessLogicLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Linq;
using System.Runtime.InteropServices;
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
    /// Interaction logic for ucRegistration.xaml
    /// </summary>
    public partial class ucRegistration : UserControl {
        private MainWindow MainWindow;

        private UserService service = new UserService();
        public ucRegistration(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
        }

        private void btnSave_Click(object sender, RoutedEventArgs e) {
            var user = new User {
                Username=txtUsername.Text,
                FirstName=txtFirstName.Text,
                LastName=txtLastName.Text,
                Password=txtPassword.Text,
                Email=txtEmail.Text

            };
            service.AddUser(user);

            MainWindow.controlPanel.Content= new ucAdministeringEmployees(MainWindow);
           
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content= new ucAdministeringEmployees(MainWindow);
        }
    }
}
