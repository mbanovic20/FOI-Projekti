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

namespace PresentationLayer.UserControls
{
    /// <summary>
    /// Interaction logic for ucChildrenTracking.xaml
    /// </summary>
    public partial class ucChildrenTracking : UserControl
    {
        private PreschoolyearService yearSerice = new PreschoolyearService();
        private ChildService childService = new ChildService();
        private GroupService groupService = new GroupService();
        MainWindow MainWindow;
        public ucChildrenTracking(MainWindow mainWindow)
        {
            MainWindow = mainWindow;
            InitializeComponent();
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            cmbYear.ItemsSource = yearSerice.GetAllYears();
        }

        private void cmbYear_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbYear.SelectedItem != null)
            {
                var groups = yearSerice.GetGroupsForYear(cmbYear.SelectedValue.ToString());
                var groupsName = new List<string>();
                foreach(var g in groups)
                {
                    groupsName.Add(g.Name);
                }
                cmbGroup.ItemsSource = groupsName;
            }
        }

        private void cmbGroup_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbGroup.SelectedItem != null)
            {
                var group = groupService.GetGroupByName(cmbGroup.SelectedValue.ToString())[0];
                dgvChildren.ItemsSource = childService.getChildremFromGroup(group);
            }
            
        }

        private void txtLastName_KeyUp(object sender, KeyEventArgs e)
        {
            dgvChildren.ItemsSource = childService.GetChildByLastName(txtLastName.Text);
        }

        

        private void btnShowNotes_Click(object sender, RoutedEventArgs e)
        {
            Child selectedChild = dgvChildren.SelectedItem as Child;
            if (dgvChildren.SelectedItem != null && selectedChild!= null)
            {
                MainWindow.controlPanel.Content = new usShowNotes(MainWindow,dgvChildren.SelectedItem as Child);
            } else
            {
                MessageBox.Show("Please choose Child");
            }

        }

        private void btnAddNote_Click(object sender, RoutedEventArgs e)
        {
            Child selectedChild = dgvChildren.SelectedItem as Child;
            if (dgvChildren.SelectedItem != null && selectedChild!=null )
            {
                MainWindow.controlPanel.Content = new ucAddNote(MainWindow, dgvChildren.SelectedItem as Child);
            } else
            {
                MessageBox.Show("Please choose Child");
            }
        }

        private void btnAddAttendance_Click(object sender, RoutedEventArgs e)
        {
            Child selectedChild = dgvChildren.SelectedItem as Child;
            if (selectedChild != null)
            {
                ucPastAttendance pastAttendanceControl = new ucPastAttendance(MainWindow, selectedChild);
                MainWindow.controlPanel.Content = pastAttendanceControl;
            } else
            {
                MessageBox.Show("Please select a child to edit.");
            }
        }
    }
}
