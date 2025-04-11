const APITokenManager = {
  tokenData: {
    accessToken: null,
    expiresAt: null,
  },

  isTokenExpired() {
    if (!this.tokenData.expiresAt) return true;
    return Date.now() >= this.tokenData.expiresAt;
  },

  async getSpotifyToken() {
    if (this.tokenData.accessToken && !this.isTokenExpired()) {
      return this.tokenData.accessToken;
    }

    const clientId = import.meta.env.VITE_SPOTIFY_CLIENT_ID;
    const clientSecret = import.meta.env.VITE_SPOTIFY_CLIENT_SECRET;

    if (!clientId || !clientSecret) {
      throw new Error("Spotify credentials not found in environment variables");
    }

    const response = await fetch("https://accounts.spotify.com/api/token", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: "Basic " + btoa(clientId + ":" + clientSecret),
      },
      body: "grant_type=client_credentials",
    });

    const data = await response.json();

    this.tokenData = {
      accessToken: data.access_token,
      expiresAt: Date.now() + (data.expires_in - 60) * 1000,
    };

    return this.tokenData.accessToken;
  },
};

export default APITokenManager;
