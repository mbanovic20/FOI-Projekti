using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer {
    public partial class Child {

        public override string ToString() {
            return FirstName; 
        }
        
    }
}
