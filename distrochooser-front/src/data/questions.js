export const questions = {
  Q1: {
    text: 'What is most important to you in a Linux distro?',
    options: [
      { label: 'a) Comfort and ease of use (e.g., simple setup, works out-of-the-box)', points: { Comfort: 3, Stability: 1 }, next: 'Q2', settings: {} },
      { label: 'b) Security and privacy (e.g., hardened against threats)', points: { Security: 3, Stability: 1 }, next: 'Q3', settings: {} },
      { label: 'c) Performance and speed (e.g., lightweight, fast on any hardware)', points: { Performance: 3, Customization: 1 }, next: 'Q3', settings: {} }
    ]
  },
  Q2: {
    text: 'What best describes your hardware or setup?',
    options: [
      { label: 'a) Modern computer, everyday tasks (browsing, office)', points: { Comfort: 2, Stability: 1 }, next: 'Q4', settings: {} },
      { label: 'b) Older hardware or low resources', points: { Performance: 2, Stability: 1 }, next: 'Q4', settings: { category: 'Old Computers' } },
      { label: 'c) Server or specialized (e.g., NAS, router)', points: { Security: 2, Customization: 1 }, next: 'Q5', settings: { category: 'Server' } }
    ]
  },
  Q3: {
    text: 'Do you prefer a graphical user interface (GUI) or command-line (console) focused system?',
    options: [
      { label: 'a) GUI (windows, menus, easy navigation)', points: { Comfort: 2, Stability: 1 }, next: 'Q4', settings: { desktop: 'All' } },
      { label: 'b) Console/Command-line (text-based, minimal)', points: { Performance: 2, Customization: 2, Comfort: -1 }, next: 'Q5', settings: { desktop: 'None' } },
      { label: 'c) No preference', points: { Customization: 1 }, next: 'Q4', settings: {} }
    ]
  },
  Q4: {
    text: 'Which desktop environment style do you like? (This sets the look and feel.)',
    options: [
      { label: 'a) Modern and polished (like Windows/Mac)', points: { Comfort: 1 }, next: 'Q5', settings: { desktop: 'GNOME' } },
      { label: 'b) Lightweight and simple', points: { Performance: 1 }, next: 'Q5', settings: { desktop: 'XFCE' } },
      { label: 'c) Highly customizable', points: { Customization: 2 }, next: 'Q5', settings: { desktop: 'Plasma' } },
      { label: 'd) No preference', points: {}, next: 'Q5', settings: { desktop: 'All' } }
    ]
  },
  Q5: {
    text: 'How do you feel about software updates?',
    options: [
      { label: 'a) Stable, infrequent (tested releases)', points: { Stability: 3, Customization: -1 }, next: 'Q6Check', settings: { rolling: 'Standard' } },
      { label: 'b) Rolling/continuous (always latest)', points: { Customization: 2, Performance: 1 }, next: 'Q6Check', settings: { rolling: 'Rolling' } },
      { label: 'c) Balanced (semi-rolling)', points: { Stability: 1, Customization: 1 }, next: 'Q6Check', settings: { rolling: 'Semi' } }
    ]
  },
  Q6: {
    text: 'Do you want to build/customize the system from scratch or minimal install?',
    options: [
      { label: 'a) Yes, I like tinkering (e.g., compile packages)', points: { Customization: 3 }, next: 'Q7', settings: { basedon: 'Arch', netinstall: 'Yes' } },
      { label: 'b) No, prefer ready-to-use', points: { Stability: 2, Comfort: 1 }, next: 'Q7', settings: { basedon: 'Debian' } },
      { label: 'c) Minimal but not too advanced', points: { Performance: 1 }, next: 'Q7', settings: { isosize: '<1GB', netinstall: 'Yes' } }
    ]
  },
  Q7: {
    text: 'Any other priorities? (Select one)',
    options: [
      { label: 'a) Gaming/Multimedia', points: { Performance: 1 }, next: 'end', settings: { category: 'Gamers' } },
      { label: 'b) Privacy-focused', points: { Security: 2 }, next: 'end', settings: { category: 'Privacy' } },
      { label: 'c) None', points: {}, next: 'end', settings: {} }
    ]
  }
};