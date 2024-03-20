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
    /// Interaction logic for ucAddChildren.xaml
    /// </summary>
    public partial class ucAddChildren : UserControl {
        private MainWindow MainWindow;
        private ChildService childservice=new ChildService();
        private GroupService groupService= new GroupService();
        public ucAddChildren(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
            LoadGroupNames();
        }
        private void LoadGroupNames() {
            cmbGrupa.ItemsSource = childservice.GetGroupNames();
        }

        private void btnSave_Click(object sender, RoutedEventArgs e) {

            {
                try {

                    var groupName = cmbGrupa.SelectedValue as string;
                    var groupId = groupService.GetGroupIdByName(groupName);
                    if (!groupId.HasValue) {
                        MessageBox.Show("Please select a valid group.");
                        return;
                    }

                    var child = new Child {
                        OIB = int.Parse(txtOIB.Text),
                        FirstName = txtFirstName.Text,
                        LastName = txtLastName.Text,
                        DateOfBirth = DateofBirth.SelectedDate,
                        Sex = txtSex.Text,
                        Adress = txtAdress.Text,
                        Nationality = txtNationality.Text,
                        DevelopmentStatus = txtDevelopmentStatus.Text,
                        MedicalInformation = txtMedicalInformation.Text,
                        BirthPlace = txtBirthPlace.Text,
                        Id_Group = groupId.Value
                    };

                    childservice.AddChild(child);
                    MainWindow.controlPanel.Content = new ucAdministeringChildren(MainWindow);
                } catch (Exception ex) {
                    MessageBox.Show($"An error occurred: {ex.Message}\n\n{ex.StackTrace}", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                }
            }
        }
 
        private void btnCancel_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content = new ucAdministeringChildren(MainWindow);
        }
    }
}

