const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const follow = require('./follow'); // function to hop multiple links by "rel"
const root = '/api/';


class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {collectionCurrently: [], pageSize: 4, links: {}};
        this.updatePageSize = this.updatePageSize.bind(this);
        this.onNavigate = this.onNavigate.bind(this);
    }

    loadFromServer(pageSize) {
        follow(client, root, [
            {rel: 'collection_currently', params: {size: pageSize}}]
                ).then(collectionCurrently => {
            console.log(collectionCurrently.entity);
            return client({
                method: 'GET',
                path: '/api/profile/currently_collection',
                headers: {'Accept': 'application/schema+json'}
            }).then(schema => {
                this.schema = schema.entity;
                return collectionCurrently;
            });
        }).done(collectionCurrently => {
            this.setState({
                collectionCurrently: collectionCurrently.entity._embedded.collection_currently,
                pageSize: pageSize,
                links: collectionCurrently.entity._links});
        });
    }

    componentDidMount() {
        this.loadFromServer(this.state.pageSize);
    }

    onNavigate(navUri) {
        client({method: 'GET', path: navUri}).done(collectionCurrently => {
            this.setState({
                collectionCurrently: collectionCurrently.entity._embedded.collection_currently,
                pageSize: this.state.pageSize,
                links: collectionCurrently.entity._links
            });
        });
    }

    updatePageSize(pageSize) {
        if (pageSize !== this.state.pageSize) {
            this.loadFromServer(pageSize);
        }
    }

    render() {
        return (
                <CollectionCurrentlyList collectionCurrently={this.state.collectionCurrently} 
                                         links={this.state.links}
                                         pageSize={this.state.pageSize}
                                         onNavigate={this.onNavigate}
                                         updatePageSize={this.updatePageSize}/>
                )
    }
}


class CollectionCurrentlyList extends React.Component {

    constructor(props) {
        super(props);
        this.handleNavFirst = this.handleNavFirst.bind(this);
        this.handleNavPrev = this.handleNavPrev.bind(this);
        this.handleNavNext = this.handleNavNext.bind(this);
        this.handleNavLast = this.handleNavLast.bind(this);
        this.handleInput = this.handleInput.bind(this);
    }

    // tag::handle-page-size-updates[]
    handleInput(e) {
        e.preventDefault();
        const pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
        if (/^[0-9]+$/.test(pageSize)) {
            this.props.updatePageSize(pageSize);
        } else {
            ReactDOM.findDOMNode(this.refs.pageSize).value =
                    pageSize.substring(0, pageSize.length - 1);
        }
    }
    // end::handle-page-size-updates[]

    // tag::handle-nav[]
    handleNavFirst(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.first.href);
    }

    handleNavPrev(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.prev.href);
    }

    handleNavNext(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.next.href);
    }

    handleNavLast(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.last.href);
    }

    render() {

        const collectionCurrently = this.props.collectionCurrently.map(dataBlock =>
                                     <CurrentlyDataBlock key={dataBlock._links.self.href} dataBlock={dataBlock}/>
        );

        const navLinks = [];
        if ("first" in this.props.links) {
            navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
        }
        if ("prev" in this.props.links) {
            navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
        }
        if ("next" in this.props.links) {
            navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
        }
        if ("last" in this.props.links) {
            navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
        }


        return (
                <div>
                    <input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
                    <table>
                        <tbody>
                            <tr>
                                <th>Time</th>
                                <th>Apparent Temperature</th>
                            </tr>
                            {collectionCurrently}
                        </tbody>
                    </table>
                    <div>
                        {navLinks}
                    </div>
                </div>
                )
    }
}

class CurrentlyDataBlock extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
                <tr>
                    <td>{this.props.dataBlock.formattedTime}</td>
                    <td>{this.props.dataBlock.apparentTemperature}</td>
                </tr>
                )
    }
}


ReactDOM.render(
        <App />,
        document.getElementById('react')
        )