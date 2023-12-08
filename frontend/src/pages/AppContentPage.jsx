import * as React from 'react';

import WelcomeContent from '../components/WelcomeContent'
import AuthContentPage from './AuthContentPage';


export default class AppContentPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            componentToShow: "welcome",
        }
    };

    goToNewPage = () => {
        window.location.href = "/currency";
    };

    render() {
        return (
            <>
                {this.state.componentToShow === "welcome" && <WelcomeContent />}
                {this.state.componentToShow === "messages" && <AuthContentPage goToNewPage={this.goToNewPage} />}
            </>
        );
    };
}