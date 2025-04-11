const SettingsManager = {
  getPreferences: () => {
    return localStorage.getItem("");
  },
  setPreferences: (param) => {
    localStorage.setItem("", param);
  },
  clearPreferences: () => {
    localStorage.removeItem("");
  },
};

export default SettingsManager;
