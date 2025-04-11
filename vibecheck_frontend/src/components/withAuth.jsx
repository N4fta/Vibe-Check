function withAuth(Component) {
  const isAuth = !!localStorage.getItem("token");
  if (isAuth) {
    return <Component />;
  } else {
    return <Redirect to="/welcome" />;
  }
}
export default withAuth;
