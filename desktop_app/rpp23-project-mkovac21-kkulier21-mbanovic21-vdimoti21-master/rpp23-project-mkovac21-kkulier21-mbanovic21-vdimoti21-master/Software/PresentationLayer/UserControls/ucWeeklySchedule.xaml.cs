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
    /// Interaction logic for ucWeeklySchedule.xaml
    /// </summary>
    public partial class ucWeeklySchedule : UserControl
    {
        private int Week { get; set; }

        public Button clickedButton { get; set; }
        private List<Day> Days { get; set; }
        private DayService DayService = new DayService();

        public TextBlock selectedDayTextBlock { get; set; }
        public ucWeeklySchedule(int week, List<Day> days)
        {
            Week = week;
            Days = days; 
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

                var listDay = DayService.getDaysByWeeklySchdule(week);

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

                        if(dayName.ToString() == d.Name.ToString())
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

        private void TextBlock_Click(object sender, RoutedEventArgs e)
        {
            if (cmbWeek.SelectedItem != null)
            {
                clickedButton = sender as Button;
                Border parentBorder = clickedButton.Parent as Border;

                if (parentBorder != null)
                {
                    int column = Grid.GetColumn(parentBorder);

                    if (column > 0 && column <= 7)
                    {
                        selectedDayTextBlock = scheduleGrid.Children
                            .OfType<TextBlock>()
                            .FirstOrDefault(tb => Grid.GetRow(tb) == 0 && Grid.GetColumn(tb) == column);

                        if (selectedDayTextBlock != null)
                        {
                            string selectedDay = selectedDayTextBlock.Text;
                            var selectedWeek = (int)cmbWeek.SelectedItem;

                            var AddEmployeeToScheduleWindow = new Windows.AddEmployeesToSchedule(selectedDay, selectedWeek, clickedButton, selectedDayTextBlock);
                            AddEmployeeToScheduleWindow.Show();
                        }
                    }
                }
            }
        }
    }
}
