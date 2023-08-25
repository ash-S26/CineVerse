import React, { useEffect, useState } from 'react';

import Carousel from 'react-material-ui-carousel';
import { Paper } from '@mui/material';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCirclePlay } from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import api from '../../api/axiosConfig';

const WatchList = () => {
  const [movies, setMovie] = useState();

  const fetchWatchList = async () => {
    const response = await api.post('/api/v1/getwatchlist', { token: localStorage.getItem('token') });
    setMovie(response.data.movies);
    console.log(response.data);
  };

  useEffect(() => {
    fetchWatchList();
  }, []);

  return (
    <div>
      {movies?.map((movie) => {
        return (
          <div>
            <div key={movie.imdbId} style={{ margin: '10px' }}>
              <div style={{ display: 'flex' }}>
                <div className="movie-poster" style={{ marginLeft: '200px' }}>
                  <img src={movie.poster} alt="" />
                </div>
                <div className="movie-title" style={{ marginLeft: '100px', width: '300px', wordBreak: 'break-word' }}>
                  <h4>{movie.title}</h4>
                </div>
                <div className="movie-buttons-container" style={{ justifyContent: 'space-around' }}>
                  <Link to={`/Trailer/${movie.trailerLink.substring(movie.trailerLink.length - 11)}`}>
                    <div className="play-button-icon-container">
                      <FontAwesomeIcon className="play-button-icon" icon={faCirclePlay} />
                    </div>
                  </Link>

                  <div className="movie-review-button-container" style={{ marginLeft: '100px' }}>
                    <Button variant="info" onClick={() => reviews(movie.imdbId)}>
                      Reviews
                    </Button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default WatchList;
