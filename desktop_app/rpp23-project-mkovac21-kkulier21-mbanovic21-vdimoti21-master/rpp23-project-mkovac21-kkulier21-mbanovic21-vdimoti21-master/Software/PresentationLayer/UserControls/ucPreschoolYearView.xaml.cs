
using EntityLayer;
using BusinessLogicLayer;
using System;
using System.Collections;
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
    /// Interaction logic for ucPreschoolYearView.xaml
    /// </summary>
    public partial class ucPreschoolYearView : UserControl
    {
        private PreschoolyearService service = new PreschoolyearService();
        private ChildService ChildService = new ChildService();
        public ucPreschoolYearView()
        {
            InitializeComponent();
        }

        private void Label_Loaded(object sender, RoutedEventArgs e)
        {
            cmbYear.ItemsSource = GetAllYears();
        }

        private List<string> GetAllYears()
        {     
                return service.GetAllYears();
            
        }

        private List<Group> GetGroupsForYear(string year)
        {
                return service.GetGroupsForYear(year);
        }
  

        private void cmbYear_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            var year = cmbYear.SelectedValue;
            dgvGroups.ItemsSource = GetGroupsForYear(year.ToString());
            dgvGroups.Columns[0].Visibility = Visibility.Collapsed;
            dgvGroups.Columns[3].Visibility = Visibility.Collapsed;
            dgvGroups.Columns[4].Visibility = Visibility.Collapsed;
            dgvGroups.Columns[5].Visibility = Visibility.Collapsed;
        }

        private void dgvGroups_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Group selectedGroup = dgvGroups.SelectedItem as Group;
            if(dgvGroups.SelectedItem != null &&  selectedGroup!=null)
            {
                    dgvChildren.ItemsSource = ChildService.getChildremFromGroup(dgvGroups.SelectedItem as Group);   
            }
            
            
        }
    }
}
