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
    /// Interaction logic for ucEditChildren.xaml
    /// </summary>
    public partial class ucEditChildren : UserControl {


        private ChildService childService= new ChildService();
        private GroupService groupService = new GroupService();
        private MainWindow MainWindow;
        private Child currentChild;
        private string selectedCombo { get; set; }
        public ucEditChildren(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
            LoadGroupNames();
        

        }
        private void LoadGroupNames() {
            cmbGrupa.ItemsSource = childService.GetGroupNames();


        }
        public void SetChildData(Child child) {
            
            currentChild = child;
            txtOIB.Text = currentChild.OIB.ToString();
            txtFirstName.Text = currentChild.FirstName;
            txtLastName.Text = currentChild.LastName;
            txtDateofBirth.SelectedDate = currentChild.DateOfBirth;
            txtSex.Text = currentChild.Sex;
            txtAdress.Text = currentChild.Adress;
            txtNationality.Text = currentChild.Nationality;
            txtDevelopmentStatus.Text = currentChild.DevelopmentStatus;
            txtMedicalInformation.Text = currentChild.MedicalInformation;
            txtBirthPlace.Text = currentChild.BirthPlace;
            cmbGrupa.SelectedValue = currentChild.Id_Group;
          
        }
        private void btnSave_Click(object sender, RoutedEventArgs e) {
          
            try {
                currentChild.OIB = int.Parse(txtOIB.Text);  
                currentChild.FirstName = txtFirstName.Text;
                currentChild.LastName = txtLastName.Text;
                currentChild.DateOfBirth = txtDateofBirth.SelectedDate.Value; 
                currentChild.Sex = txtSex.Text;
                currentChild.Adress = txtAdress.Text;
                currentChild.Nationality = txtNationality.Text;
                currentChild.DevelopmentStatus = txtDevelopmentStatus.Text;
                currentChild.MedicalInformation = txtMedicalInformation.Text;
                currentChild.BirthPlace = txtBirthPlace.Text;

               
                var selectedGroupName = cmbGrupa.SelectedItem as string; 
                if (string.IsNullOrEmpty(selectedGroupName)) {
                    MessageBox.Show("Please select a group.");
                    return;
                }

                childService.UpdateChild(currentChild, selectedGroupName);

                MainWindow.controlPanel.Content = new ucAdministeringChildren(MainWindow);
            } catch (Exception ex) {
                MessageBox.Show($"An error occurred: {ex.Message}");
            }
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content = new ucAdministeringChildren(MainWindow);

        }
    }
}
 
