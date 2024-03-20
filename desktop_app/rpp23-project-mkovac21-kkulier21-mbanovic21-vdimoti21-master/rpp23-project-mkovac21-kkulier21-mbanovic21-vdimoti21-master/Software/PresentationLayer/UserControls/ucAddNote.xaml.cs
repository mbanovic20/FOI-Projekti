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
    /// Interaction logic for ucAddNote.xaml
    /// </summary>
    public partial class ucAddNote : UserControl
    {
        MainWindow MainWindow;
        Child Child;
        NoteService service = new NoteService();
        public ucAddNote(MainWindow mainWindow, Child child)
        {
            MainWindow = mainWindow;
            Child = child;
            InitializeComponent();
        }

        private void btnCancel_Click(object sender, RoutedEventArgs e)
        {
            MainWindow.controlPanel.Content = new ucChildrenTracking(MainWindow);
        }

        private void btnAddNote_Click(object sender, RoutedEventArgs e)
        {
            var note = new Note();
            note.Behaviour = cmbBehaviour.SelectedValue.ToString();
            note.Id_child = Child.Id;
            note.Description = new TextRange(rcbDescription.Document.ContentStart, rcbDescription.Document.ContentEnd).Text;
            service.AddNote(note);
        }
        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            txtChidlName.Text = (Child.FirstName.Replace(" ","") + " " + Child.LastName.Replace(" ",""));
            cmbBehaviour.ItemsSource = new List<string> { "Bad", "Good", "Excellent" };
            
        }
    }
}
