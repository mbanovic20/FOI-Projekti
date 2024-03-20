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
using PdfSharp;
using PdfSharp.Drawing;
using PdfSharp.Pdf;
using System.Diagnostics;
using System.Reflection.Emit;

namespace PresentationLayer.UserControls
{
    /// <summary>
    /// Interaction logic for ucChildInfo.xaml
    /// </summary>
    public partial class ucChildInfo : UserControl
    {
        private Child SelectedChild { get; set; }
        private MainWindow MainWindow;
        public ucChildInfo(Child child, MainWindow mainWindow)
        {
            InitializeComponent();
            SelectedChild = child;
            MainWindow = mainWindow;
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            txtOIB.Text = SelectedChild.OIB.ToString();
            txtFirstName.Text = SelectedChild.FirstName;
            txtLastName.Text = SelectedChild.LastName;
            txtDateofBirth.Text = SelectedChild.DateOfBirth.ToString();
            txtSex.Text = SelectedChild.Sex;
            txtAdress.Text = SelectedChild.Adress;
            txtNationality.Text = SelectedChild.Nationality;
            txtDevelopmentStatus.Text = SelectedChild.DevelopmentStatus;
            txtMedicalInformation.Text = SelectedChild.MedicalInformation;
            txtBirthPlace.Text = SelectedChild.BirthPlace;
            txtGrupa.Text = SelectedChild.Id_Group.ToString();
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucAdministeringChildren(MainWindow);
        }

        private void btnPDF_Click(object sender, RoutedEventArgs e)
        {
            string[] labels = { "OIB:", "First Name:", "Last Name:", "Date of Birth:", "Sex:", "Address:", "Nationality:", "Development Status:", "Medical Information:", "Birth Place:", "Group:" };
            string[] values = { txtOIB.Text, txtFirstName.Text, txtLastName.Text, txtDateofBirth.Text, txtSex.Text, txtAdress.Text, txtNationality.Text, txtDevelopmentStatus.Text, txtMedicalInformation.Text, txtBirthPlace.Text, txtGrupa.Text };
            try
            {
                var pdfGenerator = new PDFGeneratorService("Child Information Report", labels, values);
                pdfGenerator.GeneratePDF("ChildInfo.pdf");
            } catch (Exception ex)
            {
                MessageBox.Show($"Error generating PDF: {ex.Message}");
            }
        }
    }
}
