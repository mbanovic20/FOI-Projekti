using EntityLayer;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer
{
    public class NoteRepository : IDisposable
    {
        private PMSModel Context;
        private DbSet<Note> Notes;

        public NoteRepository(PMSModel context)
        {
            Context = context;
            Notes = Context.Set<Note>();
        }

        public void AddNote(Note note)
        {
            Notes.Add(note);
            Context.SaveChanges();
        }

        public IQueryable<Note> GetNotesByChild(Child child) {
            var query = from n in Notes where n.Id_child == child.Id select n;
            return query;
        }

        public void Dispose()
        {
            Context.Dispose();
        }
    }
}
