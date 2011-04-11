%
% WcuPortfolio.cls
%
% Western Carolina University Portfolio Template
% Developed by Dr. Andrew R. Dalton
%
% You should not modifiy this file.
%
\newcommand{\WcuPortfolioUpdate}{2009/10/06}
\newcommand{\WcuPortfolioVersion}{1.0}

\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{WcuPortfolio}[\WcuPortfolioUpdate\space Version
\WcuPortfolioVersion\space by Dr. Andrew R. Dalton]


%
% This document class is based on the 'book' document class
%
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{book}}
\ProcessOptions
\LoadClass[oneside]{book}


%
% Some packages we depend upon
%
\usepackage[plainpages=false,pdfpagelabels]{hyperref} % Get nice PDF document links
\usepackage[letterpaper,margin=1in]{geometry}         % Easily set margins
\usepackage[toc,title,page]{appendix}                 % Configure appendix formatting
\usepackage{tocloft}                                  % Configure ToC Lo{F,T} formatting
\usepackage{indentfirst}                              % Always indent first paragraph
\usepackage{fancyhdr}                                 % Control page location
\usepackage{graphicx}                                 % Include figures
\usepackage{setspace}                                 % Control spacing
\usepackage{listings}                                 % Source listings

%
% Make source listings use a pretty font
%
\renewcommand{\ttdefault}{pcr}

%
% Make the table of contents entry for the list of listing say
% "List of Listings"
%
\renewcommand\lstlistlistingname{List of Listings}

%
% Initially define all of our variables as empty
%
\gdef\@graduationMonth{}
\gdef\@graduationYear{}
\gdef\@degree{}
\gdef\@major{}
\gdef\@department{}
\gdef\@documentType{}
\gdef\@communicationInstructor{}
\gdef\@ethicsInstructor{}
\gdef\@softwareEngInstructor{}
\gdef\@teamworkInstructor{}
\gdef\@systemsInstructor{}
\gdef\@algorithmsInstructor{}
\gdef\@capstoneInstructor{}
\gdef\@author{}
\gdef\@title{}

%
% Some commands to set our variables
%
\newcommand{\graduationMonth}[1]      {\ifx\empty#1\else \gdef\@graduationMonth{#1}      \fi}
\newcommand{\graduationYear}[1]       {\ifx\empty#1\else \gdef\@graduationYear{#1}       \fi}
\newcommand{\degree}[1]               {\ifx\empty#1\else \gdef\@degree{#1}               \fi}
\newcommand{\major}[1]                {\ifx\empty#1\else \gdef\@major{#1}                \fi}
\newcommand{\department}[1]           {\ifx\empty#1\else \gdef\@department{#1}           \fi}
\newcommand{\documentType}[1]         {\ifx\empty#1\else \gdef\@documentType{#1}         \fi}
\newcommand{\communicationInstructor}[1]   {\ifx\empty#1\else \gdef\@communicationInstructor{#1 -- Communication}   \fi}
\newcommand{\ethicsInstructor}[1]   {\ifx\empty#1\else \gdef\@ethicsInstructor{#1 -- Ethics}   \fi}
\newcommand{\softwareEngInstructor}[1] {\ifx\empty#1\else \gdef\@softwareEngInstructor{#1 -- Software Engineering} \fi}
\newcommand{\teamworkInstructor}[1]  {\ifx\empty#1\else \gdef\@teamworkInstructor{#1 -- Teamwork}  \fi}
\newcommand{\systemsInstructor}[1]  {\ifx\empty#1\else \gdef\@systemsInstructor{#1 -- Systems}  \fi}
\newcommand{\algorithmsInstructor}[1]   {\ifx\empty#1\else \gdef\@algorithmsInstructor{#1 -- Algorithms}   \fi}
\newcommand{\capstoneInstructor}[1]   {\ifx\empty#1\else \gdef\@capstoneInstructor{#1 -- Capstone}   \fi}
\renewcommand{\author}[1]             {\ifx\empty#1\else \gdef\@author{#1}               \fi}
\renewcommand{\title}[1]              {\ifx\empty#1\else \gdef\@title{#1}                       \fi}


%
% Some commands to get the values of our variables
%
\newcommand{\theGraduationMonth}{%
    \ifx\@graduationMonth\@empty
        \PackageError{WcuPortfolio}{'graduationMonth' has not been defined}\@ehc
    \else
        \@graduationMonth
    \fi
}

\newcommand{\theGraduationYear}{%
    \ifx\graduationYear\@empty
        \PackageError{WcuPortfolio}{'graduationYear' has not been defined}\@ehc
    \else
        \@graduationYear
    \fi
}


\newcommand{\theDegree}{%
    \ifx\degree\@empty
        \PackageError{WcuPortfolio}{'degree' has not been defined}\@ehc
    \else
        \@degree
    \fi
}

\newcommand{\theMajor}{%
    \ifx\@major\@empty
        \PackageError{WcuPortfolio}{'major' has not been defined}\@ehc
    \else
        \@major
    \fi
}


\newcommand{\theDepartment}{%
    \ifx\@department\@empty
        \PackageError{WcuPortfolio}{'department' has not been defined}\@ehc
    \else
        \@department
    \fi
}

\newcommand{\theDocumentType}{%
    \ifx\@documentType\@empty
        \PackageError{WcuPortfolio}{'documentType' has not been defined}\@ehc
    \else
        \@documentType
    \fi
}

\newcommand{\theAuthor}{%
    \ifx\@author\@empty
        \PackageError{WcuPortfolio}{'author' has not been defined}\@ehc
    \else
        \@author
    \fi
}

\newcommand{\theTitle}{%
    \ifx\@title\@empty
        \PackageError{WcuPortfolio}{'title' has not been defined}\@ehc
    \else
        \@title
    \fi
}

\newcommand{\theCommunicationInstructor}{%
    \ifx\@communicationInstructor\@empty
        \PackageError{WcuPortfolio}{'communicationInstructor' has not been defined}\@ehc
    \else
        \@communicationInstructor
    \fi
}

\newcommand{\theEthicsInstructor}{%
    \ifx\@ethicsInstructor\@empty
        \PackageError{WcuPortfolio}{'ethicsInstructor' has not been defined}\@ehc
    \else
        \@ethicsInstructor
    \fi
}

\newcommand{\theSoftwareEngInstructor}{%
    \ifx\@softwareEngInstructor\@empty
        \PackageError{WcuPortfolio}{'softwareEngInstructor' has not been defined}\@ehc
    \else
        \@softwareEngInstructor
    \fi
}

\newcommand{\theTeamworkInstructor}{%
    \ifx\@teamworkInstructor\@empty
        \PackageError{WcuPortfolio}{'teamworkInstructor' has not been defined}\@ehc
    \else
        \@teamworkInstructor
    \fi
}

\newcommand{\theSystemsInstructor}{%
    \ifx\@systemsInstructor\@empty
        \PackageError{WcuPortfolio}{'systemsInstructor' has not been defined}\@ehc
    \else
        \@systemsInstructor
    \fi
}

\newcommand{\theAlgorithmsInstructor}{%
    \ifx\@algorithmsInstructor\@empty
        \PackageError{WcuPortfolio}{'algorithmsInstructor' has not been defined}\@ehc
    \else
        \@algorithmsInstructor
    \fi
}

\newcommand{\theCapstoneInstructor}{%
    \ifx\@capstoneInstructor\@empty
        \PackageError{WcuPortfolio}{'capstoneInstructor' has not been defined}\@ehc
    \else
        \@capstoneInstructor
    \fi
}


%
% A command to control the line spacing
%
\newcommand{\newlinestretch}[1]{\renewcommand{\baselinestretch}{#1}\normalsize}


%
% Input the specified file, and add a \clearpage afterwards
%
\newcommand{\inputfile}[1]{
    \input{#1} \clearpage
}

%
% Generate a phantom section to help the 'hyperref' package understand where
% a section begins, add a line to the ToC with the specified title, then
% execute the specified command (e.g., \maketitle).
%
\newcommand{\addtotoc}[2]{
    \phantomsection
    \addcontentsline{toc}{chapter}{#1}
    #2 \clearpage
}

%
% We want our Table of Contents (ToC) to be named 'Table of Contents' rather than
% just 'Contents'
%
\renewcommand\contentsname{Table of Contents}


%
% Format of the title page
%
\renewcommand{\maketitle}{
   \begin{titlepage}
       \pagestyle{fancy}
       \begin{center}
          {\ }\\[1in]
          \newlinestretch{1.2}
          {\sc{{\Large \theTitle}}}
          \normalsize\\[2.5ex]
          \rule{4in}{0.5pt}\\[2.5ex]
          A \theDocumentType \\
          Presented to\\
          the {\theMajor} Faculty of\\
          Western Carolina University\\[2.5ex]
          \rule{4in}{0.5pt}\\[2.5ex]
          In Partial Fulfillment\\
          of the Requirements for the Degree\\
          \theDegree\\
          \theMajor\\[2.5ex]
          \rule{4in}{0.5pt}\\[2.5ex]
          by\\
          \theAuthor\\
          {\theGraduationMonth} {\theGraduationYear}\\[2.5ex]
          \rule{4in}{0.5pt}\\[2.5ex]
          Accepted by:\\
          \ifx \@capstoneInstructor      \empty \else \@capstoneInstructor\\      \fi
          \ifx \@softwareEngInstructor   \empty \else \@softwareEngInstructor\\   \fi
          \ifx \@algorithmsInstructor    \empty \else \@algorithmsInstructor\\    \fi
          \ifx \@systemsInstructor       \empty \else \@systemsInstructor\\       \fi
          \ifx \@communicationInstructor \empty \else \@communicationInstructor\\ \fi
          \ifx \@teamworkInstructor      \empty \else \@teamworkInstructor\\      \fi
          \ifx \@ethicsInstructor        \empty \else \@ethicsInstructor\\        \fi
       \end{center}
   \end{titlepage}
}


%
% Set paragraph indention to 0.5 inches
%
\setlength{\parindent}{0.5in}


%
% Make the ToC only show Chapters and Sections
%
\setcounter{tocdepth}{2}



%
% Make subsections get numbered
%
\setcounter{secnumdepth}{3}


%
% Setup the page format.  All headers are empty.  The center footer
% contains the page number.  Also, remove the headrule from all pages.
% Finally, use the 'fancy' page style (which uses all the things we
% just setup).
%
%\lhead{\empty}
%\chead{\empty}
%\rhead{\empty}
%\lfoot{\empty}
%\cfoot{\thepage}
%\rfoot{\empty}
%\renewcommand{\headrulewidth}{0pt}
%\pagestyle{fancy}


%
% Set the appendix to just have a letter not prepended by the main division
% number in the ToC (see the documentation for the 'appendix' package)
%
\renewcommand{\setthesection}{\Alph{section}}


%
% Setup the ToC and List of {Tables|Figures|Whatever} to
% include the ellipses between the name and the page number.  Also make
% the page number big enough so that three digit numbers can be used.
%
\DeclareRobustCommand{\changetochap}{%
    \renewcommand{\cftchapleader}{\cftchapfont\cftdotfill{\cftdotsep}}%
    \cftsetpnumwidth{1.5em}
}
\changetochap
