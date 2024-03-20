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
    /// Interaction logic for ucMailForm.xaml
    /// </summary>
    public partial class ucMailForm : UserControl
    {
        private UserService userService = new UserService();
        private ParentService parentService = new ParentService();
        public ucMailForm()
        {
            InitializeComponent();
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            dgvEmployees.ItemsSource = userService.getUsers();
            var columnsToHide = new List<int> { 0, 1, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15};

            foreach (var columnIndex in columnsToHide)
            {
                if (dgvEmployees.Columns.Count > columnIndex)
                    dgvEmployees.Columns[columnIndex].Visibility = Visibility.Collapsed;
            }

            dgvParents.ItemsSource = parentService.getAllParents();

            var columnsToHideParents = new List<int> { 0, 1, 4, 5, 7, 8};

            foreach (var columnIndex in columnsToHideParents)
            {
                if (dgvParents.Columns.Count > columnIndex)
                    dgvParents.Columns[columnIndex].Visibility = Visibility.Collapsed;
            }
        }

        private void btnSendEmail_Click(object sender, RoutedEventArgs e)
        {
            string subject = txtSubject.Text;
            string body = txtMessage.Text;
            List<string> mailToList = new List<string>();

            foreach (var item in dgvEmployees.SelectedItems)
            {
                var user = item as User;
                if (user != null)
                {
                    mailToList.Add(user.Email);
                }
            }

            foreach (var item in dgvParents.SelectedItems)
            {
                var parent = item as Parent;
                if(parent != null)
                {
                    mailToList.Add(parent.Email);
                }
            }

            if (mailToList.Count > 0)
            {
                foreach (var mailTo in mailToList)
                {
                    try
                    {
                        var service = new EmailService(subject, body, mailTo);
                    } catch (Exception ex)
                    {
                        MessageBox.Show($"Došlo je do greške pri slanju emaila na: {mailTo}\nGreška: {ex.Message}");
                    }
                }

                MessageBox.Show("Emailovi su uspješno poslani!");
            } else
            {
                MessageBox.Show("Nema odabranih korisnika.");
            }
        }
    }
}
