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
    /// Interaction logic for ucAdministeringEmployees.xaml
    /// </summary>
    public partial class ucAdministeringEmployees : UserControl {
        private UserService service = new UserService();
        private MainWindow MainWindow;
        public ucAdministeringEmployees(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
        }

        private void btnEditEmployee_Click(object sender, RoutedEventArgs e) {
            if (dgvEmployee.SelectedItem != null) {
                User selectedUser = (User)dgvEmployee.SelectedItem;
                ucEditEmployee editUserControl = new ucEditEmployee(MainWindow);
                editUserControl.SetEmployeeData(selectedUser); 
                MainWindow.controlPanel.Content = editUserControl;
            } else {
                MessageBox.Show("Please select an employee to edit.");
            }
        }

        private void btnRemoveEmployee_Click(object sender, RoutedEventArgs e) {
            /* User user = dgvEmployee.SelectedItem as User;

             service.DeleteUser(user);
             dgvEmployee.ItemsSource=service.GetUsers();*/


            User user = dgvEmployee.SelectedItem as User;
            if (user != null) {
                bool isDeleted = service.DeleteUser(user);
                if (isDeleted) {
                    MessageBox.Show("User successfully deleted.");
                    dgvEmployee.ItemsSource=service.GetUsers();
                } else {
                    MessageBox.Show("There was an error deleting the user.");
                }
            } else {
                MessageBox.Show("Please select a user to delete.");
            }
        }

    

        private void btnAddEmployee_Click(object sender, RoutedEventArgs e) {
            
            MainWindow.controlPanel.Content= new ucRegistration(MainWindow);
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e) {
            dgvEmployee.ItemsSource=service.GetUsers();
        }
    }
}
