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
    /// Interaction logic for ucAddActivity.xaml
    /// </summary>
    public partial class ucAddActivity : UserControl
    {
        MainWindow MainWindow;
        Day Day;
        int Week;
        ActivityService service = new ActivityService();

        public ucAddActivity(Day day, MainWindow mainWindow, int week)
        {
            InitializeComponent();
            MainWindow = mainWindow;
            Day = day;
            Week = week;
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, Day, Week);
        }

        private void btnAddActivity_Click(object sender, RoutedEventArgs e)
        {
            var activity = new DailyActivity();
            activity.Name = txtActivityName.Text;
            activity.Location = cmbLocation.SelectedValue.ToString();
            activity.StartTime = txtStartTime.Text;
            activity.EndTime = txtEndTime.Text;
            activity.Resources.Add(cmbResource.SelectedItem as Resource);
            activity.Description = new TextRange(rcbDescription.Document.ContentStart, rcbDescription.Document.ContentEnd).Text;
            service.AddActivity(activity,Day);
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            var list = new List<string>();
            list.Add("Kuhinja");
            list.Add("Dvorište");
            list.Add("Boravak");
            list.Add("Dvorana");
            cmbLocation.ItemsSource = list;          
        }
    }
}
