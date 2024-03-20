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
    /// Interaction logic for ucAdministeringChildren.xaml
    /// </summary>
    public partial class ucAdministeringChildren : UserControl {
        private MainWindow MainWindow;
        private ChildService Childservice = new ChildService();
       
        public ucAdministeringChildren(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
        }

        private void btnEditChild_Click(object sender, RoutedEventArgs e) {
            Child selectedChild = dgvChildren.SelectedItem as Child;
            if (selectedChild != null) {
                ucEditChildren editChildrenControl = new ucEditChildren(MainWindow);
                editChildrenControl.SetChildData(selectedChild);
                MainWindow.controlPanel.Content = editChildrenControl;
            } else {
                MessageBox.Show("Please select a child to edit.");
            }
        }

        private void btnRemoveChild_Click(object sender, RoutedEventArgs e) {
            Child child = dgvChildren.SelectedItem as Child;

            if (child != null) {
                bool isDeleted = Childservice.DeleteChild(child); 
                if (isDeleted) {
                    MessageBox.Show("Child successfully deleted.");
                    dgvChildren.ItemsSource = Childservice.GetAllChildren(); 
                } else {
                    MessageBox.Show("There was an error deleting the child.");
                }
            } else {
                MessageBox.Show("Please select a child to delete.");
            }
        }

        private void btnAddChild_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content= new ucAddChildren(MainWindow);

        }

        private void Grid_Loaded(object sender, RoutedEventArgs e) {

            dgvChildren.ItemsSource=Childservice.GetAllChildren();
        }

        private void txtSearch_TextChanged(object sender, TextChangedEventArgs e)
        {
            var pattern = txtSearch.Text.ToLower();
            dgvChildren.ItemsSource = Childservice.getChildrenBySearch(pattern);
        }

        private void btnShowInfo_Click(object sender, RoutedEventArgs e)
        {
            var selectedChild = dgvChildren.SelectedItem as Child;
            if (selectedChild != null)
            {
                var childInfo = new ucChildInfo(selectedChild, MainWindow);
                MainWindow.controlPanel.Content = childInfo;
            } else
            {
                MessageBox.Show("Please select a child to see information.");
            }
        }
    }
}
