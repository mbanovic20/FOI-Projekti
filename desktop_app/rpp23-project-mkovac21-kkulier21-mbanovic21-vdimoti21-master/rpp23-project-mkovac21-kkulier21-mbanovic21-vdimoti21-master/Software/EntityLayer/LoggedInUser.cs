﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer {
    public static class LoggedInUser {
        public static User User { get; set; }

        public static void Login(User user) {
            User= user;
        }

      
    }
}
