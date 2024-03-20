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
    /// Interaction logic for usShowNotes.xaml
    /// </summary>
    public partial class usShowNotes : UserControl
    {
        MainWindow MainWindow;
        NoteService service = new NoteService();
        Child Child;

        public usShowNotes(MainWindow mainWindow,Child child)
        {
            MainWindow = mainWindow;
            Child = child;
            InitializeComponent();
        }

        

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            dgvNotes.ItemsSource = service.GetNotesByChild(Child);
            txtChidlName.Text = Child.FirstName.Replace(" ","") + " " + Child.LastName.Replace(" ","");
        }

        private void btnShowNote_Click(object sender, RoutedEventArgs e)
        {
            Note selectedNote = dgvNotes.SelectedItem as Note;
            if (dgvNotes.SelectedItem != null && selectedNote != null)
            {
                ucSelectedNote.Content = new ucSelcetedNote(dgvNotes.SelectedItem as Note);
            } else
            {
                MessageBox.Show("Please choose Note");
            }
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucChildrenTracking(MainWindow);
        }
    }
}
