using BusinessLogicLayer;
using EntityLayer;
using PresentationLayer.UserControls;
using Syncfusion.Windows.Controls;
using Syncfusion.Windows.Shared;
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
using System.Windows.Shapes;

namespace PresentationLayer.Windows
{
    public partial class AddEmployeesToSchedule : Window
    {
        private string Day { get; set; }
        private int Week { get; set; }
        private Button ClickedButton { get; set; }
        private TextBlock SelectedDay {  get; set; }

        private UserService Userservices = new UserService();
        private DayService DayServices = new DayService();
        public AddEmployeesToSchedule(string day, int week, Button clickedButton, TextBlock selectedDay)
        {
            Day = day;
            Week = week;
            ClickedButton = clickedButton;
            SelectedDay = selectedDay;
            InitializeComponent();
        }

        private void Close_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            txtDay.Text = Day;
            txtWeek.Text = Week.ToString();
            dgvEmployees.ItemsSource = Userservices.getUsers();
        }

        private void addEmployeesToSchedule_Click(object sender, RoutedEventArgs e)
        {
            if(dgvEmployees.SelectedItems != null && dgvEmployees.SelectedItems.Count > 0)
            {
                var date = dpDate.SelectedDate;
                var userList = new List<User>();

                foreach (var d in dgvEmployees.SelectedItems)
                {
                    userList.Add(d as User);
                }

                var day = new Day
                {
                    Users = userList,
                    Date = date,
                    Name = Day,
                    Id_WeeklySchedule = Week
                };

                var strUser = "";
                foreach (var u in day.Users)
                {
                    strUser += u.FirstName + " " + u.LastName + ",\n";
                }

                if (day.Date != null)
                {
                    var DanDatum = day.Name + " - " + day.Date.ToString().Split(' ')[0];
                    ClickedButton.Content = DanDatum + "\n" + "\n" + strUser;
                    ClickedButton.Background = new SolidColorBrush(Color.FromRgb(2, 235, 111));
                    ClickedButton.FontWeight = FontWeights.Bold;
                    ClickedButton.FontSize = 25;
                    DayServices.addNewDay(day);
                    Close();
                } else
                {
                    MessageBox.Show("Pick a date!");
                }
            } else
            {
                MessageBox.Show("Select employees!");
            } 
        }
    }
}
