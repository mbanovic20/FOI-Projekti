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
    /// Interaction logic for UcEditAttendance.xaml
    /// </summary>
    public partial class UcEditAttendance : UserControl {
        private MainWindow MainWindow;
        private Attendance CurrentAttendance;
        private AttendanceService attendanceService = new AttendanceService();
        public UcEditAttendance(MainWindow mainWindow, Attendance attendance) {
            InitializeComponent();
            MainWindow=mainWindow;
            CurrentAttendance = attendance;

            LoadAttendanceDetails();
        }

        private void btnSave_Click(object sender, RoutedEventArgs e) {
            CurrentAttendance.Date = dataPicker.SelectedDate;
            CurrentAttendance.isPresent = cmbPrisutan.SelectedIndex == 0;
            attendanceService.UpdateAttendance(CurrentAttendance);

            MessageBox.Show("Attendance updated successfully.");

            btnClose_Click(sender, e);
            SwitchToPastAttendance();
        }
        private void SwitchToPastAttendance() {
            var pastAttendanceUC = new ucPastAttendance(MainWindow, CurrentAttendance.Child);
            MainWindow.controlPanel.Content = pastAttendanceUC;
        }

        private void btnClose_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content = new ucChildrenTracking(MainWindow);
        }
        private void LoadAttendanceDetails() {
            txtFirstName.Text = CurrentAttendance.Child.FirstName;
            txtLastName.Text = CurrentAttendance.Child.LastName;
            dataPicker.SelectedDate = CurrentAttendance.Date;
            CurrentAttendance.isPresent = cmbPrisutan.SelectedIndex == 0 ? true : false;
        }
    }
}
