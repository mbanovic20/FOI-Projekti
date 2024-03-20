
using EntityLayer;
using BusinessLogicLayer;
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
    /// Interaction logic for ucExistingGroups.xaml
    /// </summary>
    public partial class ucExistingGroups : UserControl
    {
        private MainWindow MainWindow;
        private List<Group> groups = new List<Group>();
        private GroupService service = new GroupService();
        public ucExistingGroups(MainWindow mainWindow, List<Group> groupList)
        {
            MainWindow=mainWindow;
            groups = groupList;
            InitializeComponent();

        }

        private void Add_Click(object sender, RoutedEventArgs e)
        {
            foreach (var p in dgvExistingGroups.SelectedItems )
            {
                groups.Add(p as Group);
            }
            
            MainWindow.controlPanel.Content = new addPreschoolYear(MainWindow, groups);
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            dgvExistingGroups.ItemsSource = service.GetAllGroups();
            
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new addPreschoolYear(MainWindow, groups);
        }
    }
}
