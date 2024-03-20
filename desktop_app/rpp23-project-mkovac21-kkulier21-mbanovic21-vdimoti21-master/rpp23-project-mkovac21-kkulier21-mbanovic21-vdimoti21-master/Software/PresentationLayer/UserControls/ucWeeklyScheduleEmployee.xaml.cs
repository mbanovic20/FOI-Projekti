using BusinessLogicLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
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
    /// Interaction logic for ucWeeklyScheduleEmployee.xaml
    /// </summary>
    public partial class ucWeeklyScheduleEmployee : UserControl
    {
        private int Week { get; set; }
        private List<Day> Days { get; set; }

        private DayService DayService = new DayService();
        
        MainWindow MainWindow;

        public ucWeeklyScheduleEmployee(int week, List<Day> days, MainWindow mainWindow)
        {
            Week = week;
            Days = days;
            MainWindow = mainWindow;
            InitializeComponent();
        }

        private void PopulateWeeks()
        {
            cmbWeek.ItemsSource = Enumerable.Range(1, 52).ToList();
        }

        private void weekComboBox_Loaded(object sender, RoutedEventArgs e)
        {
            PopulateWeeks();
            if (cmbWeek != null)
            {
                if (cmbWeek.SelectedItem == null)
                {
                    cmbWeek.SelectedItem = 1;
                }
            }
        }

        private void cmbWeek_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (cmbWeek.SelectedItem != null)
            {
                var week = (int)cmbWeek.SelectedItem;

                var listDay = DayService.getDaysByWeeklySchduleAndUsername(week, "admin");
                Days = listDay;

                clearButtonContent();
                fillTheSchedule(listDay);
            }
        }



        private void fillTheSchedule(List<Day> listday)
        {
            Grid grid = scheduleGrid;

            for (int i = 1; i <= 7 && i < scheduleGrid.ColumnDefinitions.Count; i++)
            {
                TextBlock dayTextBlock = scheduleGrid.Children
                    .OfType<TextBlock>()
                    .FirstOrDefault(tb => Grid.GetRow(tb) == 0 && Grid.GetColumn(tb) == i);

                if (dayTextBlock != null)
                {
                    string dayName = dayTextBlock.Text;
                    //MessageBox.Show(dayName);

                    //Day matchingDay = listday.Find(d => d.Name == dayName);
                    //var strr = "";
                    foreach (var d in listday)
                    {
                        //MessageBox.Show(d.Name + "==" + dayName + "\n");

                        if (dayName.ToString() == d.Name.ToString())
                        {
                            Border dayBorder = scheduleGrid.Children
                                .OfType<Border>()
                                .FirstOrDefault(b => Grid.GetRow(b) == 1 && Grid.GetColumn(b) == i);

                            if (dayBorder != null)
                            {
                                var UsersByDayId = DayService.getUsersByDayId(d.Id);
                                //MessageBox.Show(UsersByDayId.ToString() + "\n");
                                Button userButton = dayBorder.Child as Button;
                                if (userButton != null)
                                {
                                    string users = "";
                                    foreach (var user in UsersByDayId)
                                    {
                                        {
                                            users += user.FirstName + " " + user.LastName + ",\n";
                                        }
                                        var DanDatum = d.Name + " - " + d.Date.ToString().Split(' ')[0];
                                        userButton.Content = DanDatum + "\n" + "\n" + users;
                                        userButton.Background = new SolidColorBrush(Color.FromRgb(2, 235, 111));
                                        userButton.FontWeight = FontWeights.Bold;
                                        userButton.FontSize = 25;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private void clearButtonContent()
        {
            foreach (var border in scheduleGrid.Children.OfType<Border>())
            {
                Button userButton = border.Child as Button;
                if (userButton != null)
                {
                    userButton.Content = "";
                    userButton.Background = new SolidColorBrush(Color.FromRgb(255, 255, 255));
                }
            }
        }

        //Vedran Đimoti

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            foreach(var d in Days)
            {
                if(d.Name.ToString() == "Monday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow,d,Week);
                }
            }
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Tuesday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Wednesday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }

        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Thursday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }

        private void Button_Click_4(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Friday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }

        private void Button_Click_5(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Saturday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }

        private void Button_Click_6(object sender, RoutedEventArgs e)
        {
            foreach (var d in Days)
            {
                if (d.Name.ToString() == "Sunday")
                {
                    MainWindow.controlPanel.Content = new ucActivitySchedule(MainWindow, d, Week);
                }
            }
        }
    }
}
