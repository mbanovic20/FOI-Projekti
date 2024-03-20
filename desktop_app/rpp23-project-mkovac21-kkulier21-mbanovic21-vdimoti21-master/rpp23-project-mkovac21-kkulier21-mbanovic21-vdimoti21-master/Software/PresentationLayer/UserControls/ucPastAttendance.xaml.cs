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

namespace PresentationLayer.UserControls {
    /// <summary>
    /// Interaction logic for ucPastAttendance.xaml
    /// </summary>
    public partial class ucPastAttendance : UserControl {

        private MainWindow MainWindow;
        private AttendanceService attendanceService= new AttendanceService();
        private Child SelectedChild;
        public ucPastAttendance(MainWindow mainWindow, Child selectedChild) {
            InitializeComponent();
            MainWindow=mainWindow;
            SelectedChild = selectedChild;
            LoadPastAttendance();
        }

        private void LoadPastAttendance() {
            var pastAttendance = attendanceService.GetPastAttendanceForChild(SelectedChild.Id);
            dgvAttendance.ItemsSource = pastAttendance;
        }


        private void btnSearch_Click(object sender, RoutedEventArgs e) {
            if (datePicker.SelectedDate.HasValue) {
                DateTime selectedDate = datePicker.SelectedDate.Value;
                var filteredAttendance = attendanceService.GetAttendanceByDate(selectedDate);

                dgvAttendance.ItemsSource = filteredAttendance;
            } else {
                MessageBox.Show("Please select a date.", "Search Error", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void btnAddAttendance_Click(object sender, RoutedEventArgs e) {
            UcAddAttendence addAttendanceUC = new UcAddAttendence(MainWindow);
            addAttendanceUC.SetChildData(SelectedChild);
            this.Content = addAttendanceUC;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content = new ucChildrenTracking(MainWindow);
        }


        private void btnRemoveAttendance_Click(object sender, RoutedEventArgs e) {
            if (dgvAttendance.SelectedItem != null) {
                Attendance selectedAttendance = dgvAttendance.SelectedItem as Attendance;
                attendanceService.RemoveAttendance(selectedAttendance.Id);
                LoadPastAttendance();
            } else {
                MessageBox.Show("Please select an attendance record to remove.", "Removal Error", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }

        private void btnEditAttendance_Click(object sender, RoutedEventArgs e) {
            if (dgvAttendance.SelectedItem != null) {
                Attendance selectedAttendance = dgvAttendance.SelectedItem as Attendance;
                UcEditAttendance editAttendanceUC = new UcEditAttendance(MainWindow, selectedAttendance);
                MainWindow.controlPanel.Content = editAttendanceUC;
            } else {
                MessageBox.Show("Please select an attendance record to edit.", "Selection Missing", MessageBoxButton.OK, MessageBoxImage.Warning);
            }
        }
        private void OnAutoGeneratingColumn(object sender, DataGridAutoGeneratingColumnEventArgs e) {
            if (e.Column.Header.ToString() == "Id_User" || e.Column.Header.ToString() == "User") {
                e.Column.Visibility = Visibility.Collapsed;
            }
        }
    }
}
