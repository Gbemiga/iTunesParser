angular.module('MusicCabinetApp.Services', []).
service('Services', function () {
        var TrackList = [];
        var searchKeyword;
        var trackView;

        var addTrack = function(newObj) {
            TrackList.push(newObj);
        };

        var getTracks = function(){
            return TrackList;
        };

        var setTracks = function(allTracks){
            TrackList = allTracks;
                };
        var getsearchKeyword = function(){
            return searchKeyword;
        };

        var setsearchKeyword = function(Keyword){
            searchKeyword = Keyword;
        };

        var getTrackView = function(){
            return trackView;
        };

        var setTrackView = function(x){
            trackView = x;
        };

        return {
            addTrack: addTrack,
            getTracks: getTracks,
            setTracks: setTracks,
            getsearchKeyword: getsearchKeyword,
            setsearchKeyword: setsearchKeyword,
            getTrackView: getTrackView,
            setTrackView: setTrackView
        };
});