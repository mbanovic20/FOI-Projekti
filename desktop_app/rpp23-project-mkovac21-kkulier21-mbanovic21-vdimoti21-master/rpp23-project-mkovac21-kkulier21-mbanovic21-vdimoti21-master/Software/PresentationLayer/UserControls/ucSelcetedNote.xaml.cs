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
    /// Interaction logic for ucSelcetedNote.xaml
    /// </summary>
    public partial class ucSelcetedNote : UserControl
    {
        Note Note;
        public ucSelcetedNote(Note note)
        {
            InitializeComponent();
            Note=note;
        }

        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            txtBehaviour.Text = Note.Behaviour.ToString();
            rcbSelectedNote.Document.Blocks.Add(new Paragraph(new Run(Note.Description)));
        }
    }
}
