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
    /// Interaction logic for addPreschoolYear.xaml
    /// </summary>
    public partial class addPreschoolYear : UserControl
    {
        private MainWindow MainWindow;
        private List<Group> groups;
        private PreschoolyearService service = new PreschoolyearService();
        public addPreschoolYear(MainWindow mainWindow,List<Group> groupsList)
        {
            groups = groupsList;
            MainWindow = mainWindow;
            InitializeComponent();
        }


        private void addNewGroup_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new AddNewGroup(MainWindow, groups);
            
            
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
           dgvGroups.ItemsSource = groups;   
        }

        private void addExitingGroup_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucExistingGroups(MainWindow, groups);
        }

        private void addNewYear_Click(object sender, RoutedEventArgs e)
        {
            if (txtYear.Text!="" && groups.Count>0)
            {
                var newPreschoolYear = new PreeschoolYear();
                newPreschoolYear.Year = txtYear.Text;
                newPreschoolYear.inProgress = true;
                service.addNewPreschoolYear(newPreschoolYear,groups);
            } else
            {
                MessageBox.Show("Add Year name or add gorups");
            }
            
                
            
            
        }

    }
}
