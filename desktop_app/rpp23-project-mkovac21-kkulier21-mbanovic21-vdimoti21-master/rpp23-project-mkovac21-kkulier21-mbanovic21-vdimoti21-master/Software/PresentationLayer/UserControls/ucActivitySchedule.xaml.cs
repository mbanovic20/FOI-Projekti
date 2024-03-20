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
    /// Interaction logic for ucActivitySchedule.xaml
    /// </summary>
    public partial class ucActivitySchedule : UserControl
    {
        MainWindow MainWindow;
        Day Day;
        private int Week;
        ActivityService service = new ActivityService();

        public ucActivitySchedule(MainWindow mainWindow,Day day, int week)
        {
            Day = day;
            MainWindow = mainWindow;
            Week= week;
            InitializeComponent();
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            txtDay.Text = Day.Name.Replace(" ", "") + " " + Day.Date.ToString().Split(' ')[0];
            dgvActivitySchedule.ItemsSource = service.GetAllActivitiesByDay(Day);
            var description = dgvActivitySchedule.Columns[2];
            var location = dgvActivitySchedule.Columns[5];
            dgvActivitySchedule.Columns.RemoveAt(2);
            dgvActivitySchedule.Columns.RemoveAt(4);
            dgvActivitySchedule.Columns[0].Visibility = Visibility.Collapsed;
            dgvActivitySchedule.Columns.Insert(2, location);
            dgvActivitySchedule.Columns.Insert(5,description);
            dgvActivitySchedule.Columns[2].Width =150;
            dgvActivitySchedule.Columns[6].Visibility = Visibility.Collapsed;
            dgvActivitySchedule.Columns[7].Visibility = Visibility.Collapsed;


        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            var list = new List<Day>();
            MainWindow.controlPanel.Content = new ucWeeklyScheduleEmployee(Week, list,MainWindow);
        }

        private void btnAddNewActivity_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucAddActivity(Day, MainWindow,Week);
        }
    }
}
