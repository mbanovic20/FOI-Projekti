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
    /// Interaction logic for AddNewGroup.xaml
    /// </summary>
    public partial class AddNewGroup : UserControl
    {
        private MainWindow MainWindow;
        private List<Group> groups = new List<Group>();
        private GroupService service = new GroupService();
        public AddNewGroup(MainWindow mainWindow, List<Group> groupList)
        {
            groups = groupList;
            MainWindow = mainWindow;
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
             var group = new Group();
            group.Name = name.Text;
            group.Age = age.Text;
 
            service.AddGroup(group);
            
            groups.Add(group);
            MainWindow.controlPanel.Content = new addPreschoolYear(MainWindow,groups);
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new addPreschoolYear(MainWindow, groups);
        }
    }
}
