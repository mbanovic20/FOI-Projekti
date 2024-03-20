using DataAccessLayer;
using EntityLayer;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer
{
    public class NoteService
    {
        public void AddNote(Note note)
        {
            using (var repo = new NoteRepository(new PMSModel()))
            {
                repo.AddNote(note);
            }
        }

        public List<Note> GetNotesByChild(Child child)
        {
            using (var repo = new NoteRepository(new PMSModel()))
            {
               return repo.GetNotesByChild(child).ToList();
            }
        }
    }
}
