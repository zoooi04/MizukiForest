<%@page import="model.Users"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="model.MusicService"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@ page import="java.util.*, model.Userplaylist, model.Playlistmusic, model.Music" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>

<%
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MizukiForestPU");
    EntityManager em = emf.createEntityManager();

    MusicService ms = new MusicService(em); // pass 'em' manually
    
    //HttpSession session = request.getSession();
            Users user = (Users)session.getAttribute("user");
            
            if(user == null){
//                response.sendRedirect("login.jsp");
                request.getRequestDispatcher("view/general/login.jsp").forward(request, response);
            }           
            
            String userId = user.getUserid();
            
            //UserService userService = new UserService(mgr);
            //String userId = userService.findUserById(user.getUserid());

%>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Music Playlists</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mizukiuser.css">
    </head>

    <section id="playlist" class="incsec"> 
        <%request.setAttribute("pageTitle", "Music Playlist");%>
        <jsp:include page="/shared/title.jsp"/>
        <%@ include file="/shared/header.jsp" %>


        <div class="container mt-5 pt-5" style='padding-bottom:170px; padding-top:50px'>
            <h2>Your Playlists</h2>
            
            <%if (session.getAttribute("AddPlaylistToQueue") != null) {%>
            <%@include file="AddPlaylistToQueueSuccess.jsp"%>
            <% session.removeAttribute("AddPlaylistToQueue");}%>
            
            <%if (session.getAttribute("CreatePlaylist") != null) {%>
            <%@include file="CreatePlaylistSuccess.jsp"%>
            <% session.removeAttribute("CreatePlaylist");}%>
            
            <%if (session.getAttribute("EditPlaylist") != null) {%>
            <%@include file="EditPlaylistSuccess.jsp"%>
            <% session.removeAttribute("EditPlaylist");}%>
            
            <%if (session.getAttribute("RemoveFromPlaylist") != null) {%>
            <%@include file="RemoveFromPlaylistSuccess.jsp"%>
            <% session.removeAttribute("RemoveFromPlaylist");}%>
            
            <%if (session.getAttribute("DeletePlaylist") != null) {%>
            <%@include file="DeletePlaylistSuccess.jsp"%>
            <% session.removeAttribute("DeletePlaylist");}%>
            
            <% if (session.getAttribute("CreatePlaylistErrorMsg") != null) {  %>
            <div style="top:22px; margin:20px 0px 50px 0px; text-align:center;" class="alert alert-danger alert-dismissible fade show" role="alert" >
                <strong> ${CreatePlaylistErrorMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: red; border: 0; border-radius: 50%; width: 25px; height: 25px; padding: 0; line-height: 0; text-align: center; "></button>
            </div>

            <%session.removeAttribute("CreatePlaylistErrorMsg");}%>
            
            <% if (session.getAttribute("EditPlaylistErrorMsg") != null) {  %>
            <div style="top:22px; margin:20px 0px 50px 0px; text-align:center;" class="alert alert-danger alert-dismissible fade show" role="alert" >
                <strong> ${EditPlaylistErrorMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: red; border: 0; border-radius: 50%; width: 25px; height: 25px; padding: 0; line-height: 0; text-align: center; "></button>
            </div>

            <%session.removeAttribute("EditPlaylistErrorMsg");}%>

            <!-- Create Playlist Form -->
            <form action="<%= request.getContextPath()%>/CreatePlaylistServlet" method="post" class="mb-4">
                <div class="input-group">
                    <input type="text" name="playlistName" class="form-control" placeholder="New Playlist Name" required>
<!--                    <input type="hidden" name="userId" value="<%= session.getAttribute("userId")%>">-->
                    <input type="hidden" name="userId" value="<%= userId%>">
<!--                    <input type="hidden" name="userId" value="U2500001">-->
                    <button class="btn btn-outline-primary" type="submit">Create Playlist</button>
                </div>
            </form>


            <%
                List<Userplaylist> userPlaylists = (List<Userplaylist>) session.getAttribute("userPlaylists");
                Map<String, List<Playlistmusic>> playlistMusicMap = (Map<String, List<Playlistmusic>>) session.getAttribute("playlistMusicMap");

                if (userPlaylists != null && !userPlaylists.isEmpty()) {
                    for (Userplaylist playlist : userPlaylists) {
                        String playlistId = playlist.getPlaylistid();
            %>
            <div class="card shadow-sm mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5><%= playlist.getPlaylistname()%></h5>

                    <div>
                        <% boolean isDefaultPlaylist = "PL000000".equals(playlist.getPlaylistid()); %>
                        <!-- Add to Queue -->
                        <!--                        <button class="btn btn-sm btn-success">Add to Queue</button>-->
                        <!-- Add to Queue -->
                        <form action="<%= request.getContextPath()%>/AddPlaylistToQueueServlet" method="post" class="d-inline">
                            <input type="hidden" name="playlistId" value="<%= playlist.getPlaylistid()%>">
                            <button type="submit" class="btn btn-sm btn-success">Add to Queue</button>
                        </form>


                        <!-- Edit Playlist -->
                        <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#editModal<%= playlist.getPlaylistid()%>" <%= isDefaultPlaylist ? "disabled" : "" %>>Edit</button>
                        <!-- Edit Playlist Modal -->
                        <div class="modal fade" id="editModal<%= playlist.getPlaylistid()%>" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog">
                                <form action="<%= request.getContextPath()%>/EditPlaylistServlet" method="post" class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Playlist Name</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <input type="hidden" name="playlistId" value="<%= playlist.getPlaylistid()%>"/>
                                        <div class="mb-3">
                                            <label class="form-label">New Playlist Name</label>
                                            <input type="text" name="newName" value="<%= playlist.getPlaylistname()%>" class="form-control" required/>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">Save Changes</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <!-- Delete Playlist -->
                        <form action="<%= request.getContextPath()%>/DeletePlaylistServlet" method="post" class="d-inline" onsubmit="return confirm('Are you sure you want to delete this playlist?');">
                            <input type="hidden" name="playlistId" value="<%= playlist.getPlaylistid()%>"/>
                            <button type="submit" class="btn btn-sm btn-danger" <%= isDefaultPlaylist ? "disabled" : "" %>>Delete</button>
                        </form>
                    </div>

                </div>
                <ul class="list-group list-group-flush">
                    <%
                        List<Playlistmusic> musicList = playlistMusicMap.get(playlistId);
                        if (musicList != null && !musicList.isEmpty()) {
                            for (Playlistmusic pm : musicList) {
                                Music music = ms.findMusicById(pm.getPlaylistmusicPK().getMusicid()); // Assuming you mapped Music in Playlistmusic entity
                    %>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div>
                            <strong><%= music.getMusicname()%></strong> by <%= music.getAuthor()%>
                        </div>
                        <form action="<%= request.getContextPath()%>/RemoveMusicServlet" method="post" class="d-inline">
                            <input type="hidden" name="playlistId" value="<%= playlistId%>"/>
                            <input type="hidden" name="musicId" value="<%= music.getMusicid()%>"/>
                            <button type="submit" class="btn btn-sm btn-outline-danger">Remove</button>
                        </form>
                    </li>
                    <%
                        }
                    } else {
                    %>
                    <li class="list-group-item text-muted">No music in this playlist.</li>
                        <%
                            }
                        %>
                </ul>
            </div>
            <%
                }
            } else {
            %>
            <p class="text-muted">You don't have any playlists yet.</p>
            <%
                }
            %>
        </div>
        
        <iframe src="<%=request.getContextPath()%>/shared/FooterPlayer.jsp" 
                style="width:100%; height:150px; position:fixed; bottom:0; left:0; border:none; z-index:9999;">
        </iframe>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </section>

</html>

<script src="/js/mizukibase.js"></script>