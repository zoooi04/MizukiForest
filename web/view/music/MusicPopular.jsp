<%@ page import="java.util.List" %>
<%@ page import="model.Music" %>
<%@ page import="model.Userplaylist" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en" data-theme="mizuki_dark">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Popular Music</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="icon" type="image/x-icon" href="<%= request.getContextPath()%>/images/mizuki.png">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/mizukiuser.css">
<!--        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/achievement-style.css">-->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<!--        <script src="${pageContext.request.contextPath}/js/achievement.js"></script>-->
    </head>

    <%request.setAttribute("pageTitle", "Music Popular");%>
    <jsp:include page="/shared/title.jsp"/>
    <%@ include file="/shared/header.jsp" %>
    <section id="popular" class="popular">

        <div class="container mt-5 pt-5" style='padding-bottom: 150px; padding-top:50px'>
            <h2>Popular Music</h2>
            <%if (session.getAttribute("AddNewToPlaylist") != null) {%>
            <%@include file="AddToPlaylistSuccess.jsp"%>
            <% session.removeAttribute("AddNewToPlaylist");
                }%>

            <%if (session.getAttribute("AddNewToQueue") != null) {%>
            <%@include file="AddToQueueSuccess.jsp"%>
            <% session.removeAttribute("AddNewToQueue");
                }%>

            <%if (session.getAttribute("AddToFavourite") != null) {%>
            <%@include file="AddToFavouriteSuccess.jsp"%>
            <% session.removeAttribute("AddToFavourite");
                }%>

            <% if (session.getAttribute("AddToPlaylistErrorMsg") != null) {  %>
            <div style="top:22px; margin:20px 0px 50px 0px; text-align:center;" class="alert alert-danger alert-dismissible fade show" role="alert" >
                <strong> ${AddToPlaylistErrorMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: red; border: 0; border-radius: 50%; width: 25px; height: 25px; padding: 0; line-height: 0; text-align: center; "></button>
            </div>

            <%session.removeAttribute("AddToPlaylistErrorMsg");
                }%>

            <% if (session.getAttribute("AddToQueueErrorMsg") != null) {  %>
            <div style="top:22px; margin:20px 0px 50px 0px; text-align:center;" class="alert alert-danger alert-dismissible fade show" role="alert" >
                <strong> ${AddToQueueErrorMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: red; border: 0; border-radius: 50%; width: 25px; height: 25px; padding: 0; line-height: 0; text-align: center; "></button>
            </div>

            <%session.removeAttribute("AddToQueueErrorMsg");
                }%>

            <% if (session.getAttribute("AddToFavouriteErrorMsg") != null) {  %>
            <div style="top:22px; margin:20px 0px 50px 0px; text-align:center;" class="alert alert-danger alert-dismissible fade show" role="alert" >
                <strong> ${AddToFavouriteErrorMsg}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="background-color: red; border: 0; border-radius: 50%; width: 25px; height: 25px; padding: 0; line-height: 0; text-align: center; "></button>
            </div>

            <%session.removeAttribute("AddToFavouriteErrorMsg");
                }%>

            <div class="row">
                <%
                    List<Music> musicList = (List<Music>) session.getAttribute("musicList");
                    if (musicList != null && !musicList.isEmpty()) {
                        for (Music music : musicList) {
                %>
                <div class="col-md-4 mb-3">
                    <div class="card shadow">
                        <div class="card-body">
                            <h5 class="card-title"><%= music.getMusicname()%></h5>
                            <p class="card-text">By <%= music.getAuthor()%><br>Duration: <%= String.format("%02d:%02d:%02d", music.getMhour(), music.getMminute(), music.getMsecond())%></p>
                            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#musicModal<%= music.getMusicid()%>">Details</button>
                        </div>
                    </div>
                </div>     



                <!-- Music Modal -->
                <div class="modal fade" id="musicModal<%= music.getMusicid()%>" tabindex="-1" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title"><%= music.getMusicname()%> - Details</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <p><strong>Author:</strong> <%= music.getAuthor()%></p>
                                <p><strong>Duration:</strong> <%= String.format("%02d:%02d:%02d", music.getMhour(), music.getMminute(), music.getMsecond())%></p>
<!--                                <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addToPlaylistModal<%= music.getMusicid()%>">Add to Playlist</button>-->

                                <!-- Form to add music to an existing playlist -->
                                <form action="<%= request.getContextPath()%>/AddToPlaylistServlet" method="post">
                                    <input type="hidden" name="musicId" value="<%= music.getMusicid()%>">

                                    <!-- Dropdown for selecting playlist -->
                                    <div class="mb-3">
                                        <label for="playlistSelect" class="form-label">Select Playlist</label>
                                        <select class="form-select" id="playlistSelect" name="playlistId">
                                            <%
                                                List<Userplaylist> playlists = (List<Userplaylist>) session.getAttribute("userPlaylists");
                                                if (playlists != null && !playlists.isEmpty()) {
                                                    for (Userplaylist playlist : playlists) {
                                            %>
                                            <option value="<%= playlist.getPlaylistid()%>"><%= playlist.getPlaylistname()%></option>
                                            <%
                                                }
                                            } else {
                                            %>
                                            <option disabled>No playlists available</option>
                                            <%
                                                }
                                            %>

                                        </select>
                                    </div>

                                    <button type="submit" class="btn btn-success w-100 mt-2">Add to Playlist</button>
                                </form>

                                <form action="<%= request.getContextPath()%>/AddToQueueServlet" method="post" class="queue-form">
                                    <input type="hidden" name="musicId" value="<%= music.getMusicid()%>">
                                    <button type="submit" class="btn btn-warning w-100 mt-2">Add to Queue</button>
                                </form>


                                <form action="<%= request.getContextPath()%>/SetFavouriteServlet" method="post">
                                    <input type="hidden" name="musicId" value="<%= music.getMusicid()%>">
                                    <button type="submit" class="btn btn-danger w-100 mt-2">Set as Favourite</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <div class="col-12">
                    <p class="text-center">No music found.</p>
                </div>
                <% }%>
            </div>
        </div>

        <iframe src="<%=request.getContextPath()%>/shared/FooterPlayer.jsp" 
                style="width:100%; height:150px; position:fixed; bottom:0; left:0; border:none; z-index:9999;">
        </iframe>

<!--        <script src="${pageContext.request.contextPath}/js/achievementHandler.js"></script>-->



        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </section>

</html>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/mizukibase.js"></script>