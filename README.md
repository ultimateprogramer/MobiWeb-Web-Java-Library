##Introduction

This project aims to address the problems involved in Mobile to Web communications. This project demonstrates the use of Java servlets to work with the [Mobile Library](https://github.com/ultimateprogramer/MobiWeb-Mobile-Library).

##Use of the servlet

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        MobileMessage mobileMessage = new MobileMessage(request);

        String ParameterA = null;
        String ParameterB = null;
        String PhotoA = null;
        String FileA = null;

        if(mobileMessage.isHeaderValid()) {
            try {
                // Retrieve 2 parameters

                ParameterA = mobileMessage.getParameter("parameterA").getParameterValue();
                ParameterB = mobileMessage.getParameter("parameterB").getParameterValue();

                // Retrieve a file and a photo

                PhotoA = mobileMessage.getPhoto("photoA").getPhotoValue();
                FileA = mobileMessage.getFile("fileA").getFileValue();
            } catch (InvalidHeaderException ex) {
                Logger.getLogger(APITest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("Parameter A: " + ParameterA + " & Parameter B: " + ParameterB);
        } finally { 
            out.close();
        }
    } 

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
##Explanation

This code explains how to make use of the parameters, photos and file uploads from the Mobile library.