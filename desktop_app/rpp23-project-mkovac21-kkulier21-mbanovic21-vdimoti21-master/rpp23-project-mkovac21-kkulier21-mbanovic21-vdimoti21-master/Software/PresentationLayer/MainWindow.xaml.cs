using EntityLayer;
using PresentationLayer.UserControls;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.AccessControl;
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

namespace PresentationLayer
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        internal void RefreshData() {
            ucAdministeringEmployees ucAdministeringEmployees = new ucAdministeringEmployees(this);

            controlPanel.Content = ucAdministeringEmployees;
        }

        private void TreeViewItem_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
        {
            List<Group> groups = new List<Group>();
            controlPanel.Content = new UserControls.addPreschoolYear(this, groups);
        }

        private void TreeViewItem_MouseLeftButtonUp_1(object sender, MouseButtonEventArgs e)
        {
            controlPanel.Content = new ucPreschoolYearView();
        }

        private void TreeViewItem_MouseLeftButtonUp_2(object sender, MouseButtonEventArgs e)
        {
            var list = new List<Day>();
            controlPanel.Content = new ucWeeklyScheduleEmployee(1, list, this);
        }


        private void TreeViewItem_MouseLeftButtonUp_3(object sender, MouseButtonEventArgs e)
        {
            controlPanel.Content = new ucChildrenTracking(this);
        }

        private void TreeViewItem_MouseLeftButtonUp_4(object sender, MouseButtonEventArgs e)
        {
            var lista = new List<Day>();
            controlPanel.Content = new ucWeeklySchedule(1, lista);
        }

        private void btnLogout_Click(object sender, RoutedEventArgs e)
        {
            Window window = new Login();
            window.Show();
            this.Close();
        }

        private void TreeViewItem_MouseLeftButtonUp_5(object sender, MouseButtonEventArgs e)
        {
            controlPanel.Content = new ucMailForm();
        }

        private void TreeViewItem_MouseLeftButtonUp_6(object sender, MouseButtonEventArgs e)
        {
            ucAdministeringChildren ucAdministeringChildren = new ucAdministeringChildren(this);
            controlPanel.Content = ucAdministeringChildren;
        }

        private void TreeViewItem_MouseLeftButtonUp_7(object sender, MouseButtonEventArgs e)
        {
            ucAdministeringEmployees ucAdministeringEmployees = new ucAdministeringEmployees(this);
            controlPanel.Content = ucAdministeringEmployees;
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            var ucWelcome = new UcWelcome();
            ucWelcome.FullName = $"{LoggedInUser.User.FirstName} {LoggedInUser.User.LastName}";
            controlPanel.Content = ucWelcome;
            if (LoggedInUser.User.Id_role != 1)
            {
                treeViewItemScheduleaAddNewPreschoolYear.Visibility = Visibility.Collapsed;
                treeViewItemScheduleManager.Visibility = Visibility.Collapsed;
                treeViewNavigationAdministrating.Visibility= Visibility.Collapsed;
                           
               
            }
        }
    }
}
