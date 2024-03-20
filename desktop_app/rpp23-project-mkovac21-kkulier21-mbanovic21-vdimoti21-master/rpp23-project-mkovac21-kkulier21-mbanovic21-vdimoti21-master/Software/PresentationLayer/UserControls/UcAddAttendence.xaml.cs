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
    /// Interaction logic for UcAddAttendence.xaml
    /// </summary>
    public partial class UcAddAttendence : UserControl {

        
        private MainWindow MainWindow;
        private Child currentChild;

        private AttendanceService attendanceService = new AttendanceService();
        public UcAddAttendence(MainWindow mainWindow) {
            InitializeComponent();
            MainWindow=mainWindow;
         
        }

        public void SetChildData(Child child) {
            currentChild = child;
            txtFirstName.Text = child.FirstName ?? string.Empty;
            txtLastName.Text = child.LastName ?? string.Empty;
        }

        private void btnRecord_Click(object sender, RoutedEventArgs e) {
            var attendance = new Attendance {
                Date = dataPicker.SelectedDate.HasValue ? dataPicker.SelectedDate.Value : DateTime.Today,
                isPresent = cmbPrisutan.SelectedIndex == 0,
                Id_Child = currentChild?.Id
            };

            attendanceService.AddAttendance(attendance);
            if (MainWindow != null && MainWindow.controlPanel != null) {
                MainWindow.controlPanel.Content = new ucPastAttendance(MainWindow, currentChild);
                
            }

        }

        private void btnClose_Click(object sender, RoutedEventArgs e) {
            MainWindow.controlPanel.Content = new ucChildrenTracking(MainWindow);
        }
    }
}

