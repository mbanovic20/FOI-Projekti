﻿using EntityLayer;
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
    /// Interaction logic for UcWelcome.xaml
    /// </summary>
    public partial class UcWelcome : UserControl {
        public UcWelcome() {
            InitializeComponent();
        }

        public string FullName {
            get { return NameTextBlock.Text; }
            set { NameTextBlock.Text = value; }
        }
    }
}
